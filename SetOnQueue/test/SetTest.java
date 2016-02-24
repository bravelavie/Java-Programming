import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Kun Liu
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert!set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert!set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size
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

        Set<String> s = this.constructorTest();

        Set<String> sExpected = this.constructorRef();

        /*
         * 
         * Assert that values of variables match expectations
         * 
         */

        assertEquals(sExpected, s);

    }

    /**
     * 
     * Test {@code add} by adding a string to an existing set.
     * 
     */

    @Test

    public final void testAdd1() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest("abc", "def");

        Set<String> sExpected = this.createFromArgsRef("abc", "def", "ghi");

        /*
         * 
         * Call method under test
         * 
         */

        s.add("ghi");

        /*
         * 
         * Assert that values of variables match expectations
         * 
         */

        assertEquals(sExpected, s);

    }

    /**
     * 
     * Test {@code add} by adding a string to an empty set.
     * 
     */

    @Test

    public final void testAdd2() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest();

        Set<String> sExpected = this.createFromArgsRef("ghi");

        /*
         * 
         * Call method under test
         * 
         */

        s.add("ghi");

        /*
         * 
         * Assert that values of variables match expectations
         * 
         */

        assertEquals(sExpected, s);

    }

    /**
     * 
     * Test {@code remove} on set with size=1.
     * 
     */

    @Test

    public final void testRemove1() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest("abc");

        Set<String> sExpected = this.createFromArgsRef();

        /*
         * 
         * Call method under test
         * 
         */

        s.remove("abc");

        /*
         * 
         * Assert that values of variables match expectations
         * 
         */

        assertEquals(sExpected, s);

    }

    @Test

    public final void testRemove2() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest("abc", "def", "jhi");

        Set<String> sExpected = this.createFromArgsRef("abc", "def");

        /*
         * 
         * Call method under test
         * 
         */

        s.remove("jhi");

        /*
         * 
         * Assert that values of variables match expectations
         * 
         */

        assertEquals(sExpected, s);

    }

    /**
     * 
     * Test {@code removeAny} on set with size=1.
     * 
     */

    @Test

    public final void testRemoveAny1() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest("abc");

        Set<String> sExpected = this.createFromArgsRef();

        /*
         * 
         * Call method under test
         * 
         */

        s.removeAny();

        /*
         * 
         * Assert that values of variables match expectations
         * 
         */

        assertEquals(sExpected, s);

    }

    /**
     * 
     * Test {@code removeAny} on set.
     * 
     */

    @Test

    public final void testRemoveAny2() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest("abc", "def", "jhi");

        Set<String> sExpected1 = this.createFromArgsRef("abc", "def");

        Set<String> sExpected2 = this.createFromArgsRef("jhi", "def");

        Set<String> sExpected3 = this.createFromArgsRef("abc", "jhi");

        /*
         * 
         * Call method under test
         * 
         */

        s.removeAny();

        assertTrue(s.equals(sExpected1) || s.equals(sExpected2)

        || s.equals(sExpected3));

    }

    /**
     * 
     * Test {@code contains} on set with size=0.
     * 
     */

    @Test

    public final void contains1() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest();

        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         * 
         */

        assertEquals(false, s.contains("abc"));

    }

    /**
     * 
     * Test {@code contains} that return true.
     * 
     */

    @Test

    public final void contains2() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest("abc", "def", "jhi");

        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         * 
         */

        assertEquals(true, s.contains("abc"));

    }

    /**
     * 
     * Test {@code contains} that return false.
     * 
     */

    @Test

    public final void contains3() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest("abc", "def", "jhi");

        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         * 
         */

        assertEquals(false, s.contains("jkl"));

    }

    /**
     * 
     * Check if {@code size} returns 0 for an empty set.
     * 
     */

    @Test

    public final void testLengthOnEmptySequence() {

        /*
         * 
         * Set up variables
         * 
         */

        Set<String> s = this.createFromArgsTest();

        /*
         * 
         * Assert that values of variables match expectations
         * 
         */

        assertEquals(0, s.size());

    }

    /**
     * 
     * Check if {@code size} returns 3 for a set of size 3.
     * 
     */

    @Test

    public final void testLengthOnLongSequence() {

        Set<String> s = this.createFromArgsTest("abc", "def", "ghi");

        /*
         * 
         * Assert that values of variables match expectations
         * 
         */

        assertEquals(3, s.size());

    }

}
