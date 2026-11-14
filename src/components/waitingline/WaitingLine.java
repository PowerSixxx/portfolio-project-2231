package components.waitingline;

/**
 * A waiting line with inspection, concatenation, and priority operations.
 *
 * @param <T>
 *            type of entries
 * @author Baowen Liu
 */
public interface WaitingLine<T> extends WaitingLineKernel<T> {

    /**
     * Reports the first entry without changing the line.
     *
     * @return the first entry
     * @aliases reference returned by front
     * @requires this is not empty
     * @ensures &lt;front&gt; is a prefix of this
     */
    T front();

    /**
     * Moves every entry of q to the back of this line, in order.
     *
     * @param q
     *            source line
     * @updates this
     * @clears q
     * @requires q != null and q != this and no entry in q is in this
     * @ensures this = #this * #q
     */
    void append(WaitingLine<T> q);

    /**
     * Reports an entry's zero-based position without changing the line.
     *
     * @param x
     *            entry to locate
     * @return the position of x, with zero denoting the front
     * @requires x != null and x is in this
     * @ensures this[pos] = x
     */
    int pos(T x);

    /**
     * Inserts a new entry at the front, preserving the existing order.
     *
     * @param x
     *            entry to add
     * @aliases reference x
     * @updates this
     * @requires x != null and x is not in this
     * @ensures this = &lt;x&gt; * #this
     */
    void insertAtFront(T x);

    /**
     * Moves an existing entry to the front, preserving the others' order.
     *
     * @param x
     *            entry to prioritize
     * @updates this
     * @requires x != null and x is in this
     * @ensures there exist strings a, b such that
     *          #this = a * &lt;x&gt; * b and this = &lt;x&gt; * a * b
     */
    void moveToFront(T x);
}
