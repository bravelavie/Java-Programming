import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires
     *
     *           <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     *           </pre>
     *
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert!map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires
     *
     *           <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     *           </pre>
     *
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert!map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Test default constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test {@code add} by adding a pair to an existing map.
     */
    @Test
    public final void testAdd1() {

        Map<String, String> m = this.createFromArgsTest("1", "abc", "2", "def");
        Map<String, String> mExpected = this.createFromArgsRef("1", "abc", "2",
                "def", "3", "ghi");

        m.add("3", "ghi");

        assertEquals(mExpected, m);
    }

    /**
     * Test {@code add} by adding a pair to an empty map.
     */
    @Test
    public final void testAdd2() {

        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("1", "abc");

        m.add("1", "abc");

        assertEquals(mExpected, m);
    }

    /**
     * Test {@code remove} on map with size=1.
     */
    @Test
    public final void testRemove1() {

        Map<String, String> m = this.createFromArgsTest("1", "abc");
        Map<String, String> mExpected = this.createFromArgsRef();

        m.remove("1");

        assertEquals(mExpected, m);
    }

    /**
     * Test {@code remove} on map with size=3.
     */
    @Test
    public final void testRemove2() {

        Map<String, String> m = this.createFromArgsTest("1", "abc", "2", "def",
                "3", "ghi");
        Map<String, String> mExpected = this.createFromArgsRef("1", "abc", "2",
                "def");

        m.remove("3");

        assertEquals(mExpected, m);
    }

    /**
     * Test {@code removeAny} on Map with size=3.
     */
    @Test
    public final void testRemoveAny1() {

        Map<String, String> m = this.createFromArgsTest("1", "abc", "2", "def",
                "3", "ghi");
        Map<String, String> mExpected1 = this.createFromArgsRef("1", "abc", "2",
                "def");
        Map<String, String> mExpected2 = this.createFromArgsRef("1", "abc", "3",
                "ghi");
        Map<String, String> mExpected3 = this.createFromArgsRef("3", "ghi", "2",
                "def");

        m.removeAny();

        assertTrue(m.equals(mExpected1) || m.equals(mExpected2)
                || m.equals(mExpected3));
    }

    /**
     * Test {@code removeAny} on Map with size=1.
     */
    @Test
    public final void testRemoveAny2() {

        Map<String, String> m = this.createFromArgsTest("1", "abc");
        Map<String, String> mExpected = this.createFromArgsRef();

        m.removeAny();

        assertEquals(mExpected, m);
    }

    /**
     * Test {@code value} method.
     */
    @Test
    public final void testValue() {
        Map<String, String> m = this.createFromArgsTest("1", "abc", "2", "def",
                "3", "ghi");
        String s = m.value("1");
        assertEquals(s, "abc");
    }

    /**
     * Test {@code hasKey} method.
     */
    @Test
    public final void testHasKey1() {
        Map<String, String> m = this.createFromArgsTest("1", "abc", "2", "def",
                "3", "ghi");
        assertTrue(m.hasKey("1") && !m.hasKey("4"));
    }

    /**
     * Test {@code hasKey} method for empty map.
     */
    @Test
    public final void testHasKey2() {
        Map<String, String> m = this.createFromArgsTest();

        assertTrue(!m.hasKey("4"));
    }

    /**
     * Test {@code size} method.
     */
    @Test
    public final void testSize() {
        Map<String, String> m = this.createFromArgsTest("1", "abc", "2", "def",
                "3", "ghi");

        assertEquals(m.size(), 3);
    }

    /**
     * Test {@code size} method with size zero.
     */
    @Test
    public final void testSizeZero() {
        Map<String, String> m = this.constructorTest();

        assertEquals(m.size(), 0);
    }
}
