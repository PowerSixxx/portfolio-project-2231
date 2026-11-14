package components.waitingline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

/** Simple tests for kernel and Standard methods. */
public final class WaitingLine1Test {

    /** A new line is empty; new entries join the back. */
    @Test
    public void testAddAndLength() {
        WaitingLine<String> line = new WaitingLine1<String>();
        assertEquals(0, line.length());
        assertEquals("<>", line.toString());
        line.addToLine("A");
        assertEquals(1, line.length());
        assertEquals("<A>", line.toString());
        line.addToLine("B");
        assertEquals(2, line.length());
        assertEquals("<A, B>", line.toString());
    }

    /** Entries leave in FIFO order and an emptied line can be reused. */
    @Test
    public void testRemoveFromLine() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        line.addToLine("B");
        assertEquals("A", line.removeFromLine());
        assertEquals("<B>", line.toString());
        assertEquals("B", line.removeFromLine());
        assertEquals(0, line.length());
        line.addToLine("C");
        assertEquals("<C>", line.toString());
    }

    /** Contains handles empty, present, and absent entries. */
    @Test
    public void testContains() {
        WaitingLine<String> line = new WaitingLine1<String>();
        assertFalse(line.contains("A"));
        line.addToLine("A");
        line.addToLine("B");
        line.addToLine("C");
        assertTrue(line.contains("A"));
        assertTrue(line.contains("B"));
        assertTrue(line.contains("C"));
        assertFalse(line.contains("D"));
        assertEquals("<A, B, C>", line.toString());
    }

    /** Remove handles middle, last, first, and only entries. */
    @Test
    public void testRemovePositions() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        line.addToLine("B");
        line.addToLine("C");
        line.addToLine("D");
        assertEquals("B", line.remove("B"));
        assertEquals("<A, C, D>", line.toString());
        assertEquals("D", line.remove("D"));
        assertEquals("<A, C>", line.toString());
        assertEquals("A", line.remove("A"));
        assertEquals("<C>", line.toString());
        assertEquals("C", line.remove("C"));
        assertEquals("<>", line.toString());
    }

    /** Clear works on empty and populated lines. */
    @Test
    public void testClear() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.clear();
        assertEquals("<>", line.toString());
        line.addToLine("A");
        line.addToLine("B");
        line.clear();
        assertEquals(0, line.length());
        line.addToLine("C");
        assertEquals("<C>", line.toString());
    }

    /** New instances are empty and independent. */
    @Test
    public void testNewInstance() {
        WaitingLine<String> line = new WaitingLine1<String>();
        WaitingLine<String> empty = line.newInstance();
        assertNotSame(line, empty);
        assertEquals("<>", empty.toString());
        line.addToLine("A");
        WaitingLine<String> fresh = line.newInstance();
        assertEquals(line.getClass(), fresh.getClass());
        assertEquals("<>", fresh.toString());
        fresh.addToLine("B");
        assertEquals("<A>", line.toString());
        assertEquals("<B>", fresh.toString());
    }

    /** Transfer works when the receiver is empty. */
    @Test
    public void testTransferIntoEmpty() {
        WaitingLine<String> line = new WaitingLine1<String>();
        WaitingLine<String> source = new WaitingLine1<String>();
        line.transferFrom(source);
        assertEquals("<>", line.toString());
        assertEquals("<>", source.toString());
        source.addToLine("A");
        source.addToLine("B");
        line.transferFrom(source);
        assertEquals("<A, B>", line.toString());
        assertEquals("<>", source.toString());
    }

    /** Transfer replaces old contents without sharing state. */
    @Test
    public void testTransferReplaces() {
        WaitingLine<String> line = new WaitingLine1<String>();
        WaitingLine<String> source = new WaitingLine1<String>();
        line.addToLine("old");
        source.addToLine("A");
        line.transferFrom(source);
        assertEquals("<A>", line.toString());
        assertEquals("<>", source.toString());
        source.addToLine("B");
        assertEquals("<A>", line.toString());
        assertEquals("<B>", source.toString());
        source.clear();
        line.transferFrom(source);
        assertEquals("<>", line.toString());
        assertEquals("<>", source.toString());
    }

    /** Iteration preserves the line and follows arrival order. */
    @Test
    public void testIterator() {
        WaitingLine<String> line = new WaitingLine1<String>();
        assertFalse(line.iterator().hasNext());
        line.addToLine("A");
        line.addToLine("B");
        Iterator<String> entries = line.iterator();
        assertTrue(entries.hasNext());
        assertEquals("A", entries.next());
        assertTrue(entries.hasNext());
        assertEquals("B", entries.next());
        assertFalse(entries.hasNext());
        assertEquals("<A, B>", line.toString());
    }

    /** Duplicate entries are rejected. */
    @Test(expected = AssertionError.class)
    public void testAddDuplicate() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        line.addToLine("A");
    }

    /** Null entries are rejected. */
    @Test(expected = AssertionError.class)
    public void testAddNull() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine(null);
    }

    /** An empty line has no entry to remove. */
    @Test(expected = AssertionError.class)
    public void testRemoveFromEmpty() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.removeFromLine();
    }

    /** The entry being removed must exist. */
    @Test(expected = AssertionError.class)
    public void testRemoveMissing() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        line.remove("B");
    }

    /** A line cannot transfer from itself. */
    @Test(expected = AssertionError.class)
    public void testTransferSelf() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.transferFrom(line);
    }

    /** Iterator removal is unsupported. */
    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        WaitingLine<String> line = new WaitingLine1<String>();
        line.addToLine("A");
        Iterator<String> entries = line.iterator();
        entries.next();
        entries.remove();
    }
}
