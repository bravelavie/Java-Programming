import components.set.Set;
import components.set.Set1L;

/**
 * Layered implementations of secondary methods {@code add} and {@code remove}
 * for {@code Set}.
 *
 * @param <T>
 *            type of {@code Set} elements
 */
public final class SetSecondary1L<T> extends Set1L<T> {

    /**
     * Default constructor.
     */
    public SetSecondary1L() {
        super();
    }

    @Override
    public Set<T> remove(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        Set<T> temp = s.newInstance();
        Set<T> result = s.newInstance();
        temp.transferFrom(this);
        for (T x : temp) {
            if (s.contains(x)) {
                result.add(x);
            } else {
                this.add(x);
            }
        }
        return result;
    }

    @Override
    public void add(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        Set<T> temp = s.newInstance();
        temp.transferFrom(s);
        for (T x : temp) {
            if (!this.contains(x)) {
                this.add(x);
            } else {
                s.add(x);
            }
        }
    }
}