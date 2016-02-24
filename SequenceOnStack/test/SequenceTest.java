import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Test default constructor.
     *
     */

    @Test

    public final void testDefaultConstructor() {

        /*
         *
         * Set up variables and call method under test
         *
         */

        Sequence<String> s = this.constructorTest();

        Sequence<String> sExpected = this.constructorRef();

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code add} by adding a string to an existing sequence.
     *
     */

    @Test

    public final void testAdd() {

        /*
         *
         * Set up variables
         *
         */

        Sequence<String> s = this.createFromArgsTest("abc", "def");

        Sequence<String> sExpected = this

        .createFromArgsRef("abc", "def", "ghi");

        /*
         *
         * Call method under test
         *
         */

        s.add(s.length(), "ghi");

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code remove} by removing the last string from the sequence.
     *
     */

    @Test

    public final void testRemove1() {

        /*
         *
         * Set up variables
         *
         */

        Sequence<String> s = this.createFromArgsTest("abc", "def");

        Sequence<String> sExpected = this.createFromArgsRef("abc");

        /*
         *
         * Call method under test
         *
         */

        s.remove(s.length());

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code remove} on sequence with length=1.
     *
     */

    @Test

    public final void testRemove2() {

        /*
         *
         * Set up variables
         *
         */

        Sequence<String> s = this.createFromArgsTest("abc");

        Sequence<String> sExpected = this.createFromArgsRef();

        /*
         *
         * Call method under test
         *
         */

        s.remove(s.length());

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Check if {@code length} returns 0 for an empty sequence.
     *
     */

    @Test

    public final void testLengthOnEmptySequence() {

        /*
         *
         * Set up variables
         *
         */

        Sequence<String> s = this.createFromArgsTest();

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(0, s.length());

    }

    /**
     *
     * Check if {@code length} returns 3 for a sequence of length 3.
     *
     */

    @Test
    public final void testLengthOnLongSequence() {

        Sequence<String> s = this.createFromArgsTest("abc", "def", "ghi");

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(3, s.length());
    }
}
