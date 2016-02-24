import components.sequence.Sequence;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 *
 * @author Put your name here
 *
 */
public final class SequenceSmoothfunction {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceSmoothfunction() {
    }

    /**
     * Smooth a given {@code Sequence<Integer>}.
     *
     * @param s1
     *            the sequence to smooth
     * @returns s2 the smoothed sequence
     * @requires |s1| >= 1
     * @ensures
     * 
     *          <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     *          </pre>
     */
    public static Sequence<Integer> smooth2(Sequence<Integer> s1) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s1.length() >= 1 : "|s1| >= 1";

        Sequence<Integer> s2 = s1.newInstance();
        Sequence<Integer> temp = s1.newInstance();

        temp.transferFrom(s1);

        while (temp.length() > 1) {
            int j = temp.remove(temp.length() - 1);
            int i = temp.remove(temp.length() - 1);

            int k = 0;
            if ((i < 0 && j > 0) || (j > 0 && i < 0)) {
                k = (i + j) / 2;
            } else {
                if (i < j) {
                    k = i + (j - i) / 2;
                } else {
                    k = j + (i - j) / 2;
                }
            }

            s2.add(0, k);
            temp.add(temp.length(), i);
            s1.add(0, j);
        }
        s1.add(0, temp.remove(0));
        return s2;
    }

    /**
     * Smooth a given {@code Sequence<Integer>} using recursive method.
     *
     * @param s1
     *            the sequence to smooth
     * @returns s2 the smoothed sequence
     * @requires |s1| >= 1
     * @ensures
     * 
     *          <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     *          </pre>
     */
    public static Sequence<Integer> smooth(Sequence<Integer> s1) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s1.length() >= 1 : "|s1| >= 1";

        Sequence<Integer> s2 = s1.newInstance();

        if (s1.length() > 1) {
            int j = s1.remove(s1.length() - 1);
            int i = s1.remove(s1.length() - 1);
            int k = 0;
            if ((i < 0 && j > 0) || (j > 0 && i < 0)) {
                k = (i + j) / 2;
            } else {
                if (i < j) {
                    k = i + (j - i) / 2;
                } else {
                    k = j + (i - j) / 2;
                }
            }

            s1.add(s1.length(), i);
            if (s1.length() > 1) {
                s2 = smooth(s1);
            }
            s2.add(s2.length(), k);
            s1.add(s1.length(), j);
        }

        return s2;
    }
}