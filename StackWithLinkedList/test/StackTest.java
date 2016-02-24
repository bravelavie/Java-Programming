import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    // TODO - add test cases for constructor, push, pop, and length

    /**
     * Test default constructor.
     */

    @Test

    public final void testDefaultConstructor() {

        /*
         *
         * Set up variables and call method under test
         *
         */

        Stack<String> s = this.constructorTest();

        Stack<String> sExpected = this.constructorRef();

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code push} by adding a string to top of empty stack.
     *
     */

    @Test

    public final void testPush1() {

        /*
         *
         * Set up variables
         *
         */

        Stack<String> s = this.createFromArgsTest();

        Stack<String> sExpected = this.createFromArgsRef("abc");

        /*
         *
         * Call method under test
         *
         */

        s.push("abc");

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code push} by adding a string to top of regular stack.
     *
     */

    @Test

    public final void testPush2() {

        /*
         *
         * Set up variables
         *
         */

        Stack<String> s = this.createFromArgsTest("def", "ghi");

        Stack<String> sExpected = this.createFromArgsRef("abc", "def", "ghi");

        /*
         *
         * Call method under test
         *
         */

        s.push("abc");

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code pop} by removing the string on top of stack.
     *
     */

    @Test

    public final void testPop1() {

        /*
         *
         * Set up variables
         *
         */

        Stack<String> s = this.createFromArgsTest("abc");

        Stack<String> sExpected = this.createFromArgsRef();

        /*
         *
         * Call method under test
         *
         */

        s.pop();

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Test {@code pop} by removing the string on top of stack.
     *
     */

    @Test

    public final void testPop2() {

        /*
         *
         * Set up variables
         *
         */

        Stack<String> s = this.createFromArgsTest("abc", "def", "ghi");

        Stack<String> sExpected = this.createFromArgsRef("def", "ghi");

        /*
         *
         * Call method under test
         *
         */

        s.pop();

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(sExpected, s);

    }

    /**
     *
     * Check if {@code length} returns 0 for an empty stack.
     *
     */

    @Test

    public final void testLength1() {

        /*
         *
         * Set up variables
         *
         */

        Stack<String> s = this.createFromArgsTest();

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(0, s.length());

    }

    /**
     *
     * Check if {@code length} returns 0 for an empty stack of length 3.
     *
     */

    @Test

    public final void testLength2() {

        Stack<String> s = this.createFromArgsTest("abc", "def", "ghi");

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals(3, s.length());

    }

    /**
     *
     * Test {@code top} by removing the string on top of stack.
     *
     */

    @Test

    public final void testTop() {

        /*
         *
         * Set up variables
         *
         */

        Stack<String> s = this.createFromArgsTest("abc", "def", "ghi");

        /*
         *
         * Assert that values of variables match expectations
         *
         */

        assertEquals("abc", s.top());
    }

    /**
     *
     * Test {@code replaceTop} by removing the string on top of stack.
     *
     */

    @Test

    public final void testreplaceTop() {

        /*
         * 
         * Set up variables
         *
         */

        Stack<String> s = this.createFromArgsTest("abc", "def", "ghi");
        Stack<String> sExpected = this.createFromArgsRef("jkl", "def", "ghi");
        String x = s.replaceTop("jkl");

        /*
         *
         * Assert that values of variables match expectations
         *
         */
        assertEquals(x, "abc");
        assertEquals(s, sExpected);
    }

}
