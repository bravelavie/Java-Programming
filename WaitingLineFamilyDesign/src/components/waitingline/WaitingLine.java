package components.waitingline;

/**
 * {@code WaitingLineKernel} enhanced with secondary methods.
 *
 * @param <T>
 *            type of {@code WaitingLine} entries
 * @mathdefinitions <pre>
 * IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 *
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))
 * </pre>
 */
public interface WaitingLine<T> extends WaitingLineKernel<T> {

    /**
     * Reports whether {@code x} is in {@code this}.
     *
     * @param x
     *            the entry to be checked
     * @return true iff entry is in {@code this}
     * @ensures contains = (x is in this)
     */
    boolean contains(T x);

    /**
     * Reports the position of entry in {@code this}.
     *
     * @param x
     *            the entry to be located
     * @return the position of the entry
     * @aliases reference returned by {@code position}
     * @requires x is in this
     */
    int position(T x);

    /**
     * Removes entry at position {@code pos} of {@code this}, and returns it.
     *
     * @param pos
     *            the position of entry to be removed
     * @return the entry removed
     * @updates this
     * @requires 0 <= pos and pos < |this|
     * @ensures <pre>
     * #this = this.left * <remove> * this.right
     * </pre>
     */
    T remove(int pos);

}
