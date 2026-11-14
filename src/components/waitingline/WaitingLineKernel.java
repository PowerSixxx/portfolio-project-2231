package components.waitingline;

import components.standard.Standard;

/**
 * An ordered waiting line containing distinct, non-null entries. Entries are
 * compared using {@code equals}; clients must preserve their equality and hash
 * codes while they are in a line. The first entry is served first.
 *
 * @param <T>
 *            type of entries
 * @mathmodel type WaitingLineKernel is modeled by a string of T with no null
 *            entries and no two entries equal according to equals
 * @initially this = &lt;&gt;
 * @iterator ~this.seen * ~this.unseen = this
 * @author Baowen Liu
 */
public interface WaitingLineKernel<T>
        extends Standard<WaitingLine<T>>, Iterable<T> {

    /**
     * Adds an entry to the back of this line.
     *
     * @param x
     *            entry to add
     * @aliases reference x
     * @updates this
     * @requires x != null and x is not in this
     * @ensures this = #this * &lt;x&gt;
     */
    void addToLine(T x);

    /**
     * Removes and returns the entry at the front.
     *
     * @return the former first entry
     * @aliases reference returned by removeFromLine
     * @updates this
     * @requires this is not empty
     * @ensures #this = &lt;removeFromLine&gt; * this
     */
    T removeFromLine();

    /**
     * Reports the number of entries without changing the line.
     *
     * @return number of entries
     * @ensures length = |this|
     */
    int length();

    /**
     * Reports whether this line contains an entry equal to x.
     *
     * @param x
     *            entry to look for
     * @return whether x is in this line
     * @requires x != null
     * @ensures contains = (x is in this)
     */
    boolean contains(T x);

    /**
     * Removes the stored entry equal to x, preserving all other entries' order.
     *
     * @param x
     *            entry to remove
     * @return the stored entry equal to x
     * @updates this
     * @requires x != null and x is in this
     * @ensures there exist strings a, b such that #this = a * &lt;remove&gt; *
     *          b and this = a * b and remove = x
     */
    T remove(T x);
}
