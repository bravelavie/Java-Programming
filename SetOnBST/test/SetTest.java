import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Kun Liu, Son Le
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
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
            assert !set.contains(s) : "Violation of: every entry in args is unique";
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
            assert !set.contains(s) : "Violation of: every entry in args is unique";
                    set.add(s);
        }
        return set;
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
         */

        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
    }

    /**
     *
     * Test {@code add} by adding a string to an empty tree
     *
     */

    @Test
    public final void testAddWithEmptryTree() {
        Set<String> s = this.createFromArgsTest();
        s.add("ghi");
        Set<String> sExpected = this.createFromArgsRef("ghi");
        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code add} by adding a string to a tree with 1 element
     *
     */

    @Test
    public final void testAddWith1ElementTree() {
        Set<String> s = this.createFromArgsTest("abc");
        s.add("ghi");
        Set<String> sExpected = this.createFromArgsRef("abc", "ghi");
        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code add} by adding a string to a tree with 1 element
     *
     */

    @Test
    public final void testAddWith1ElementTreewithDifferentOrder() {
        Set<String> s = this.createFromArgsTest("ghi");
        s.add("abc");
        Set<String> sExpected = this.createFromArgsRef("abc", "ghi");
        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code add} by adding a string to a tree with 1 left branch
     *
     */

    @Test
    public final void testAddWithTree1LeftBranch() {
        Set<String> s = this.createFromArgsTest("abc", "def");
        s.add("ghi");
        Set<String> sExpected = this.createFromArgsRef("abc", "def", "ghi");
        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code add} by adding a string to a tree with 1 right branch
     *
     */

    @Test
    public final void testAddWithTree1RightBranch() {
        Set<String> s = this.createFromArgsTest("abc", "ghi");
        s.add("def");
        Set<String> sExpected = this.createFromArgsRef("abc", "def", "ghi");
        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code add} by adding a string to an existing set.
     *
     */

    @Test
    public final void testAddWith1RightAnd1LeftTree() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("abc", "def", "ghi");
        s.add("jkl");

        Set<String> sExpected = this.createFromArgsRef("abc", "def", "ghi",
                "jkl");
        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code add} by adding a string to an existing set.
     *
     */

    @Test
    public final void testAddWithMultipleLevelsTree() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("b", "a", "d", "c", "e");
        s.add("f");
        Set<String> sExpected = this.createFromArgsRef("b", "a", "d", "c", "e",
                "f");
        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);

    }

    @Test
    public final void testAddWithMultipleLevelsTreeWithLeftAndRightNotEmpty() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("d", "b", "a", "c", "f", "e",
                "g");
        s.add("k");
        Set<String> sExpected = this.createFromArgsRef("d", "b", "a", "c", "f",
                "e", "g", "k");
        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code remove} on set with size=1.
     *
     */

    @Test
    public final void testRemoveWith1Element() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("abc");
        Set<String> sExpected = this.createFromArgsRef();

        /*
         * 
         * Call method under test
         */

        String str = s.remove("abc");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("abc", str);

    }

    @Test
    public final void testRemoveWith3ElementsTree1Left1RightTree() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("def", "abc", "jhi");
        Set<String> sExpected = this.createFromArgsRef("abc", "def");

        /*
         * 
         * Call method under test
         */

        s.remove("jhi");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);

    }

    @Test
    public final void testRemoveWith2ElementsTree1Left() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("def", "abc");
        Set<String> sExpected = this.createFromArgsRef("abc");

        /*
         * 
         * Call method under test
         */

        String str = s.remove("def");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("def", str);
    }

    @Test
    public final void testRemoveWith2ElementsTree1LeftNotRemoveRoot() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("def", "abc");
        Set<String> sExpected = this.createFromArgsRef("def");

        /*
         * 
         * Call method under test
         */

        String str = s.remove("abc");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("abc", str);
    }

    @Test
    public final void testRemoveWith2ElementsTree1Right() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("a", "b");
        Set<String> sExpected = this.createFromArgsRef("a");

        /*
         * 
         * Call method under test
         */

        String str = s.remove("b");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("b", str);
    }

    @Test
    public final void testRemoveWith2ElementsTree1RightRemoveRoot() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("a", "b");
        Set<String> sExpected = this.createFromArgsRef("b");

        /*
         * 
         * Call method under test
         */

        String str = s.remove("a");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("a", str);
    }

    @Test
    public final void testRemoveWith2ElementsTree1Right0LeftRemoveRoot() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("abc", "def");
        Set<String> sExpected = this.createFromArgsRef("def");

        /*
         * 
         * Call method under test
         */

        String str = s.remove("abc");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("abc", str);
    }

    @Test
    public final void testRemoveWith2ElementsTree1Right0LeftNotRemoveRoot() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("abc", "def");

        Set<String> sExpected = this.createFromArgsRef("abc");

        /*
         * 
         * Call method under test
         */

        String str = s.remove("def");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("def", str);
    }

    @Test
    public final void testRemoveWithTree3BranchesRemoveRoot1Left1Right() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("def", "abc", "hij");
        Set<String> sExpected = this.createFromArgsRef("abc", "hij");

        /*
         * 
         * Call method under test
         */

        String str = s.remove("def");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("def", str);
    }

    @Test
    public final void testRemoveWithMultipleLevelTreeRemoveRoot() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("d", "a", "c", "b", "e");

        Set<String> sExpected = this.createFromArgsRef("a", "c", "b", "e");

        /*
         * 
         * Call method under test
         */

        String str = s.remove("d");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("d", str);
    }

    @Test
    public final void testRemoveWithMultipleLevelsTreeWithLeftAndRightNotEmpty() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("d", "b", "a", "c", "f", "e",
                "g", "k");
        String str = s.remove("k");

        Set<String> sExpected = this.createFromArgsRef("d", "b", "a", "c", "f",
                "e", "g");
        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("k", str);

    }

    @Test
    public final void testRemoveWithMultipleLevelsTreeWithLeftAndRightNotEmptyAndRemoveRoot() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("d", "b", "a", "c", "f", "e",
                "g", "k");
        String str = s.remove("d");

        Set<String> sExpected = this.createFromArgsRef("b", "a", "c", "f", "e",
                "g", "k");
        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("d", str);

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
         */

        Set<String> s = this.createFromArgsTest("a");

        Set<String> sExpected = this.createFromArgsRef();

        /*
         * 
         * Call method under test
         */

        String str = s.removeAny();

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertEquals(sExpected, s);
        assertEquals("a", str);

    }

    @Test
    public final void testRemoveAnyWith2BranchesTree1Right() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("a", "b");

        Set<String> sExpected1 = this.createFromArgsRef("a");
        Set<String> sExpected2 = this.createFromArgsRef("b");

        /*
         * 
         * Call method under test
         */

        String str = s.removeAny();

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertTrue(s.equals(sExpected1) || s.equals(sExpected2));
        assertTrue(str.equals("a") || str.equals("b"));

    }

    @Test
    public final void testRemoveAnyWith2BranchesTree1Left() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("b", "a");

        Set<String> sExpected1 = this.createFromArgsRef("a");
        Set<String> sExpected2 = this.createFromArgsRef("b");

        /*
         * 
         * Call method under test
         */

        String str = s.removeAny();

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertTrue(s.equals(sExpected1) || s.equals(sExpected2));
        assertTrue(str.equals("a") || str.equals("b"));

    }

    @Test
    public final void testRemoveAnyWithClassic1Right1Left() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("a", "c", "b");

        Set<String> sExpected1 = this.createFromArgsRef("a", "b");
        Set<String> sExpected2 = this.createFromArgsRef("b", "c");
        Set<String> sExpected3 = this.createFromArgsRef("a", "c");

        /*
         * 
         * Call method under test
         */

        String str = s.removeAny();

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertTrue(s.equals(sExpected1) || s.equals(sExpected2)
                || s.equals(sExpected3));
        assertTrue(str.equals("a") || str.equals("b") || str.equals("c"));

    }

    /**
     *
     * Test {@code removeAny} on set.
     *
     */

    @Test
    public final void testRemoveAny2Brances() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("abc", "def", "jhi");

        Set<String> sExpected1 = this.createFromArgsRef("abc", "def");

        Set<String> sExpected2 = this.createFromArgsRef("jhi", "def");

        Set<String> sExpected3 = this.createFromArgsRef("abc", "jhi");

        /*
         * 
         * Call method under test
         */

        String str = s.removeAny();

        assertTrue(s.equals(sExpected1) || s.equals(sExpected2)

                || s.equals(sExpected3));
        assertTrue(str.equals("abc") || str.equals("def") || str.equals("jhi"));

    }

    @Test
    public final void testRemoveAnyWithMultipleLevel() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("d", "b", "a", "c", "f", "e",
                "g", "k");
        String str = s.removeAny();

        Set<String> sExpected1 = this.createFromArgsRef("b", "a", "c", "f",
                "e", "g", "k");
        Set<String> sExpected2 = this.createFromArgsRef("d", "b", "a", "c",
                "e", "g", "k");
        Set<String> sExpected3 = this.createFromArgsRef("d", "b", "c", "f",
                "e", "g", "k");
        Set<String> sExpected4 = this.createFromArgsRef("d", "b", "a", "f",
                "e", "g", "k");
        Set<String> sExpected5 = this.createFromArgsRef("d", "b", "a", "f",
                "e", "g", "k");
        Set<String> sExpected6 = this.createFromArgsRef("d", "b", "a", "c",
                "f", "g", "k");
        Set<String> sExpected7 = this.createFromArgsRef("d", "b", "a", "k",
                "f", "e", "c");

        /*
         * 
         * Assert that values of variables match expectations
         */

        assertTrue(sExpected1.equals(s) || sExpected2.equals(s)
                || sExpected3.equals(s) || sExpected4.equals(s)
                || sExpected5.equals(s) || sExpected6.equals(s)
                || sExpected7.equals(s));
        assertTrue(str.equals("a") || str.equals("b") || str.equals("c")
                || str.equals("d") || str.equals("e") || str.equals("f")
                || str.equals("g") || str.equals("k"));

    }

    /**
     *
     * Test {@code contains} on set with size=0.
     *
     */

    @Test
    public final void containsWithSize0() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest();
        Set<String> expect = this.createFromArgsRef();

        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(false, s.contains("abc"));
        assertEquals(expect, s);

    }

    /**
     *
     * Test {@code contains} that return true.
     *
     */

    @Test
    public final void containsWith1Node() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("abc");
        Set<String> expect = this.createFromArgsRef("abc");

        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(true, s.contains("abc"));
        assertEquals(s, expect);

    }

    /**
     *
     * Test {@code contains} that return false.
     *
     */

    @Test
    public final void containsWith1Left1Right() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("def", "abc", "jhi");
        Set<String> expect = this.createFromArgsRef("def", "abc", "jhi");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(false, s.contains("jkl"));
        assertEquals(s, expect);

    }

    @Test
    public final void containsWith1Left() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("b", "a");
        Set<String> expect = this.createFromArgsRef("b", "a");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(false, s.contains("c"));
        assertEquals(s, expect);

    }

    @Test
    public final void containsWithRightBranch() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("a", "b");
        Set<String> expect = this.createFromArgsRef("a", "b");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(true, s.contains("a"));
        assertEquals(s, expect);

    }

    @Test
    public final void containsWith1Right1LeftBranch() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("b", "d", "c");
        Set<String> expect = this.createFromArgsRef("b", "d", "c");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(false, s.contains("a"));
        assertEquals(s, expect);

    }

    @Test
    public final void containsWithMultipleBranches() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("d", "b", "a", "e", "f");
        Set<String> expect = this.createFromArgsRef("d", "b", "a", "e", "f");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(true, s.contains("a"));
        assertEquals(s, expect);

    }

    @Test
    public final void containsWithSpecialBranches() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("d", "b", "a", "c", "f", "e",
                "g", "k");
        Set<String> expect = this.createFromArgsRef("d", "b", "a", "c", "f",
                "e", "g", "k");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(true, s.contains("d"));
        assertEquals(s, expect);

    }

    /**
     *
     * Test {@code size} of empty set.
     *
     */

    @Test
    public final void testSize0() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest();
        Set<String> expect = this.createFromArgsRef();
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(0, s.size());
        assertEquals(expect, s);

    }

    @Test
    public final void testSize1() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("b");
        Set<String> expect = this.createFromArgsRef("b");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(1, s.size());
        assertEquals(expect, s);

    }

    @Test
    public final void testSize1Left() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("b", "a");
        Set<String> expect = this.createFromArgsRef("b", "a");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(2, s.size());
        assertEquals(expect, s);

    }

    @Test
    public final void testSize1Right() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("a", "b");
        Set<String> expect = this.createFromArgsRef("a", "b");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(2, s.size());
        assertEquals(expect, s);

    }

    @Test
    public final void testSize1Right1Left() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("b", "a", "c");
        Set<String> expect = this.createFromArgsRef("b", "a", "c");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(3, s.size());
        assertEquals(expect, s);

    }

    @Test
    public final void testSizeMultipleLevels() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("d", "b", "a", "e", "f");
        Set<String> expect = this.createFromArgsRef("d", "b", "a", "e", "f");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(5, s.size());
        assertEquals(expect, s);

    }

    @Test
    public final void testSizeMultipleLevelsSpecial() {

        /*
         * 
         * Set up variables
         */

        Set<String> s = this.createFromArgsTest("d", "b", "a", "c", "f", "e",
                "g", "k");
        Set<String> expect = this.createFromArgsRef("d", "b", "a", "c", "f",
                "e", "g", "k");
        /*
         * 
         * Call method under test and assert that values of variables match
         * 
         * expectations
         */

        assertEquals(8, s.size());
        assertEquals(expect, s);

    }
}
