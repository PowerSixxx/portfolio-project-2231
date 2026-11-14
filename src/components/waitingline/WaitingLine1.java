package components.waitingline;

import java.util.Iterator;

import components.sequence.Sequence;
import components.sequence.Sequence2L;

/**
 * Waiting line represented by Sequence2L.
 *
 * @param <T>
 *            type of entries
 * @convention rep != null; every entry of rep is non-null; no two entries of
 *             rep are equal according to equals
 * @correspondence this = the entries of rep, in their sequence order
 * @author Baowen Liu
 */
public final class WaitingLine1<T> extends WaitingLineSecondary<T> {

    /** Representation, ordered from front to back. */
    private Sequence<T> rep;

    /**
     * Creates an empty representation.
     *
     * @replaces rep
     * @ensures rep = &lt;&gt;
     */
    private void createNewRep() {
        this.rep = new Sequence2L<T>();
    }

    /**
     * Creates an empty waiting line.
     *
     * @ensures this = &lt;&gt;
     */
    public WaitingLine1() {
        this.createNewRep();
    }

    /**
     * Finds an entry directly in the representation.
     *
     * @param x
     *            entry to find
     * @return its position, or -1 if absent
     * @requires x != null
     * @ensures positionOf = -1 iff x is not in rep; otherwise rep[positionOf] =
     *          x
     */
    private int positionOf(T x) {
        int position = -1;
        int index = 0;
        Iterator<T> entries = this.rep.iterator();
        while (position < 0 && entries.hasNext()) {
            if (entries.next().equals(x)) {
                position = index;
            }
            index++;
        }
        return position;
    }

    @Override
    public void addToLine(T x) {
        assert x != null : "Violation of: x != null";
        assert this.positionOf(x) < 0 : "Violation of: x is not in this";
        this.rep.add(this.rep.length(), x);
    }

    @Override
    public T removeFromLine() {
        assert this.rep.length() > 0 : "Violation of: this is not empty";
        return this.rep.remove(0);
    }

    @Override
    public int length() {
        return this.rep.length();
    }

    @Override
    public boolean contains(T x) {
        assert x != null : "Violation of: x != null";
        return this.positionOf(x) >= 0;
    }

    @Override
    public T remove(T x) {
        assert x != null : "Violation of: x != null";
        int position = this.positionOf(x);
        assert position >= 0 : "Violation of: x is in this";
        return this.rep.remove(position);
    }

    @Override
    public void clear() {
        this.createNewRep();
    }

    @Override
    public WaitingLine<T> newInstance() {
        return new WaitingLine1<T>();
    }

    @Override
    public void transferFrom(WaitingLine<T> source) {
        assert source != null : "Violation of: source != null";
        assert source != this : "Violation of: source != this";
        assert source instanceof WaitingLine1<?> : "Violation of: matching dynamic types";
        WaitingLine1<T> localSource = (WaitingLine1<T>) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    @Override
    public Iterator<T> iterator() {
        return this.rep.iterator();
    }
}
