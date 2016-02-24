package components.waitingline;

import java.util.Iterator;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 *
 * WaitingLine class extending WaitingLineSecondary.
 *
 * @author
 *
 * @param <T>
 */
public class WaitingLine1<T> extends WaitingLineSecondary<T> {

    /*
     * Private members
     */

    /**
     * Concrete representation of WaitingLine.
     */
    private Sequence<T> s;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.s = new Sequence1L<T>();
    }

    /*
     * Public members
     */

    /**
     * Creates a new representation for {@code this}.
     */
    public WaitingLine1() {
        this.createNewRep();
    }

    /*
     * Kernel
     * methods------------------------------------------------------------
     */

    @Override
    public final void enqueue(T x) {
        assert this.s != null : "Violation of: this is not null.";

        this.s.add(this.s.length(), x);
    }

    @Override
    public final T seat() {
        assert this.s != null : "Violation of: this is not null.";
        assert this.s.length() > 0 : "Violation of: this /= <>";
        return this.s.remove(0);
    }

    @Override
    public final int length() {
        assert this.s != null : "Violation of: this is not null.";
        return this.s.length();
    }

    @Override
    public final boolean hasEntry(T entry) {
        assert this.s != null : "Violation of: this is not null.";
        assert this.s.length() > 0 : "Violation of: this /= <>";

        boolean isTrue = false;
        for (int i = 0; i < this.s.length(); i++) {
            if (this.s.entry(i).equals(entry)) {
                isTrue = true;
                break;
            }
        }

        return isTrue;
    }

    /*
     * Standard methods
     * ----------------------------------------------------------
     * --------------------------
     */

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void clear() {
        this.createNewRep();

    }

    @SuppressWarnings("unchecked")
    @Override
    public final WaitingLine1<T> newInstance() {
        try {
            return this.getClass().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass());
        }

    }

    @Override
    public final void transferFrom(WaitingLine<T> source) {
        assert source != null : "Violation of: source is not null.";
        assert source != this : "Violation of: source is not this.";
        assert source instanceof WaitingLine1<?> : ""
        + "Violation of: source is of dynamic type WaitingLine1<?>";

        WaitingLine1<T> localSource = (WaitingLine1<T>) source;
        this.s = localSource.s;
        localSource.createNewRep();
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Iterator<T> iterator() {

        this.s.iterator();
        return null;
    }

}