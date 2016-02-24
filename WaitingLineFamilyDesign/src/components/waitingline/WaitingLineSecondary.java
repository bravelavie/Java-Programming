package components.waitingline;

import java.util.Iterator;

import components.list.List;
import components.list.List3;

/**
 * Layered implementations of secondary methods for {@code Queue}.
 *
 * <p>
 * Execution-time performance of {@code front}, {@code replaceFront},
 * {@code append}, and {@code flip} as implemented in this class is O(|
 * {@code this}|). Execution-time performance of {@code sort} implemented in
 * this class is O(|{@code this}| log |{@code this}|) expected, O(|{@code this}
 * |^2) worst case.
 *
 * @param <T>
 *            type of {@code Queue} entries
 */
public abstract class WaitingLineSecondary<T> implements WaitingLine<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private List<T> s;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.s = new List3<T>();
    }

    /*
     * Public members ---------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public WaitingLineSecondary() {
        this.createNewRep();
    }

    /*
     * Common methods (from Object) -------------------------------------------
     */

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WaitingLine<?>)) {
            return false;
        }
        WaitingLine<?> q = (WaitingLine<?>) obj;
        if (this.length() != q.length()) {
            return false;
        }
        Iterator<T> it1 = this.iterator();
        Iterator<?> it2 = q.iterator();
        while (it1.hasNext()) {
            T x1 = it1.next();
            Object x2 = it2.next();
            if (!x1.equals(x2)) {
                return false;
            }
        }
        return true;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int hashCode() {
        final int samples = 2;
        final int a = 37;
        final int b = 17;
        int result = 0;
        /*
         * This code makes hashCode run in O(1) time. It works because of the
         * iterator order string specification, which guarantees that the (at
         * most) samples entries returned by the it.next() calls are the same
         * when the two Queues are equal.
         */
        int n = 0;
        Iterator<T> it = this.iterator();
        while (n < samples && it.hasNext()) {
            n++;
            T x = it.next();
            result = a * result + b * x.hashCode();
        }
        return result;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            if (it.hasNext()) {
                result.append(",");
            }
        }
        result.append(">");
        return result.toString();
    }

    /*
     * Other non-kernel methods -----------------------------------------------
     */

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public boolean contains(T x) {
        assert x != null : "Violation of: x is not null";
        boolean result = false;
        this.s.moveToStart();
        while (this.s.rightLength() > 0) {
            if (this.s.rightFront().equals(x)) {
                result = true;
            }
            this.s.advance();
        }
        return result;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int position(T x) {
        assert this.contains(x) : "Violation of: x is in this";
        this.s.moveToStart();
        while (!this.s.rightFront().equals(x)) {
            this.s.advance();
        }
        return this.s.leftLength();
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public T remove(int pos) {
        assert pos >= 0 : "Position cannot be negative";
        assert pos <= this.length() : "Position cannot exceed length of waitingline";
        this.s.moveToStart();
        while (pos > this.s.leftLength()) {
            this.s.advance();
        }
        return this.s.removeRightFront();

    }
}