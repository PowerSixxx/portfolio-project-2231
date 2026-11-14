package components.waitingline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** Simple tests for secondary and Object methods. */
public final class WaitingLineTest {

    /** Front reports the first entry without removing it. */
    @Test
    public void testFront() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        assertEquals("A", line.front());
        assertEquals("<A>", line.toString());
        line.addToLine("B");
        assertEquals("A", line.front());
        assertEquals("<A, B>", line.toString());
    }

    /** Positions start at zero and do not change the line. */
    @Test
    public void testPosition() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        assertEquals(0, line.pos("A"));
        line.addToLine("B");
        line.addToLine("C");
        assertEquals(0, line.pos("A"));
        assertEquals(1, line.pos("B"));
        assertEquals(2, line.pos("C"));
        assertEquals("<A, B, C>", line.toString());
    }

    /** Append handles empty source and receiver lines. */
    @Test
    public void testAppendEmpty() {
        WaitingLine<String> line = new WaitingLine1<String>();
        WaitingLine<String> source = new WaitingLine1<String>();
        line.append(source);
        assertEquals("<>", line.toString());
        assertEquals("<>", source.toString());
        source.addToLine("A");
        line.append(source);
        assertEquals("<A>", line.toString());
        assertEquals("<>", source.toString());
        line.append(source);
        assertEquals("<A>", line.toString());
        assertEquals("<>", source.toString());
    }

    /** Append keeps order, clears the source, and avoids sharing. */
    @Test
    public void testAppendNonempty() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        WaitingLine<String> source = new WaitingLine1<String>();
        source.addToLine("B");
        source.addToLine("C");
        line.append(source);
        assertEquals("<A, B, C>", line.toString());
        assertEquals("<>", source.toString());
        source.addToLine("D");
        assertEquals("<A, B, C>", line.toString());
        assertEquals("<D>", source.toString());
    }

    /** Insert handles empty, singleton, and longer lines. */
    @Test
    public void testInsertAtFront() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.insertAtFront("C");
        assertEquals("<C>", line.toString());
        line.insertAtFront("B");
        assertEquals("<B, C>", line.toString());
        line.insertAtFront("A");
        assertEquals("<A, B, C>", line.toString());
    }

    /** Move handles a singleton and every position in a longer line. */
    @Test
    public void testMoveToFront() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        line.moveToFront("A");
        assertEquals("<A>", line.toString());
        line.addToLine("B");
        line.addToLine("C");
        line.moveToFront("A");
        assertEquals("<A, B, C>", line.toString());
        line.moveToFront("B");
        assertEquals("<B, A, C>", line.toString());
        line.moveToFront("C");
        assertEquals("<C, B, A>", line.toString());
    }

    /** Equal lines have equal hash codes and preserve their contents. */
    @Test
    public void testEqualsAndHashCode() {
        WaitingLine<String> first = new WaitingLine1<String>();
        WaitingLine<String> second = new WaitingLine1<String>();
        assertTrue(first.equals(second));
        assertEquals(first.hashCode(), second.hashCode());
        first.addToLine("A");
        first.addToLine("B");
        second.addToLine("A");
        second.addToLine("B");
        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals("<A, B>", first.toString());
        assertEquals("<A, B>", second.toString());
    }

    /** Different sizes, orders, entries, and types are unequal. */
    @Test
    public void testEqualsDifferent() {
        WaitingLine<String> first = new WaitingLine1<String>();
        first.addToLine("A");
        first.addToLine("B");
        WaitingLine<String> second = new WaitingLine1<String>();
        second.addToLine("A");
        assertFalse(first.equals(second));
        second.addToLine("C");
        assertFalse(first.equals(second));
        second.clear();
        second.addToLine("B");
        second.addToLine("A");
        assertFalse(first.equals(second));
        assertFalse(first.equals(null));
        assertFalse(first.equals("<A, B>"));
        assertEquals("<A, B>", first.toString());
        assertEquals("<B, A>", second.toString());
    }

    /** Text output handles empty, singleton, and longer lines. */
    @Test
    public void testToString() {
        WaitingLine<String> line = new WaitingLine1<String>();
        assertEquals("<>", line.toString());
        line.addToLine("A");
        assertEquals("<A>", line.toString());
        assertEquals(1, line.length());
        line.addToLine("B");
        assertEquals("<A, B>", line.toString());
        assertEquals(2, line.length());
        assertEquals("A", line.removeFromLine());
        assertEquals("B", line.removeFromLine());
    }

    /** An empty line has no front entry. */
    @Test(expected = AssertionError.class)
    public void testFrontEmpty() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.front();
    }

    /** A position query requires an existing entry. */
    @Test(expected = AssertionError.class)
    public void testPositionMissing() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        line.pos("B");
    }

    /** An entry already in the line cannot be inserted again. */
    @Test(expected = AssertionError.class)
    public void testInsertDuplicate() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        line.insertAtFront("A");
    }

    /** Only an existing entry can move to the front. */
    @Test(expected = AssertionError.class)
    public void testMoveMissing() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        line.moveToFront("B");
    }

    /** A line cannot append itself. */
    @Test(expected = AssertionError.class)
    public void testAppendSelf() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.append(line);
    }

    /** The lines being appended must not share entries. */
    @Test(expected = AssertionError.class)
    public void testAppendOverlapping() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        WaitingLine<String> source = new WaitingLine1<String>();
        source.addToLine("A");
        line.append(source);
    }
}
