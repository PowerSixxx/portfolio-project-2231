package components.waitingline;

import java.util.Iterator;

/**
 * Representation-independent implementations using only kernel, Standard, and
 * Iterable operations.
 *
 * @param <T>
 *            type of entries
 * @author Baowen Liu
 */
public abstract class WaitingLineSecondary<T> implements WaitingLine<T> {

    /** Initializes the representation-independent layer. */
    protected WaitingLineSecondary() {
    }

    /**
     * Prepends a new entry using only kernel and Standard operations.
     *
     * @param x
     *            entry to add
     * @aliases reference x
     * @updates this
     * @requires x != null and x is not in this
     * @ensures this = &lt;x&gt; * #this
     */
    private void prepend(T x) {
        int oldLength = this.length();
        this.addToLine(x);
        for (int i = 0; i < oldLength; i++) {
            T first = this.removeFromLine();
            this.addToLine(first);
        }
    }

    @Override
    public final T front() {
        assert this.length() > 0 : "Violation of: this is not empty";
        return this.iterator().next();
    }

    @Override
    public final void append(WaitingLine<T> q) {
        assert q != null : "Violation of: q != null";
        assert q != this : "Violation of: q != this";
        for (T entry : q) {
            assert !this.contains(entry) : "Violation of: lines are disjoint";
        }
        while (q.length() > 0) {
            this.addToLine(q.removeFromLine());
        }
    }

    @Override
    public final int pos(T x) {
        assert x != null : "Violation of: x != null";
        assert this.contains(x) : "Violation of: x is in this";
        int position = 0;
        for (T entry : this) {
            if (entry.equals(x)) {
                return position;
            }
            position++;
        }
        return -1;
    }

    @Override
    public final void insertAtFront(T x) {
        assert x != null : "Violation of: x != null";
        assert !this.contains(x) : "Violation of: x is not in this";
        this.prepend(x);
    }

    @Override
    public final void moveToFront(T x) {
        assert x != null : "Violation of: x != null";
        assert this.contains(x) : "Violation of: x is in this";
        T stored = this.remove(x);
        this.prepend(stored);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WaitingLine<?>)) {
            return false;
        }
        WaitingLine<?> other = (WaitingLine<?>) obj;
        if (this.length() != other.length()) {
            return false;
        }
        Iterator<T> left = this.iterator();
        Iterator<?> right = other.iterator();
        while (left.hasNext()) {
            if (!left.next().equals(right.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final int hashCode() {
        final int multiplier = 31;
        int hash = 1;
        for (T entry : this) {
            hash = multiplier * hash + entry.hashCode();
        }
        return hash;
    }

    @Override
    public final String toString() {
        StringBuilder text = new StringBuilder("<");
        Iterator<T> entries = this.iterator();
        while (entries.hasNext()) {
            text.append(entries.next());
            if (entries.hasNext()) {
                text.append(", ");
            }
        }
        text.append('>');
        return text.toString();
    }
}
