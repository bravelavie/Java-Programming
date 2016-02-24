import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Kun Liu, Son Le
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero
    /**
     * Test default constructor with no argument.
     */
    @Test
    public final void testConstructor() {
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(this.constructorTest(), this.constructorRef());
    }

    /**
     * Test default constructor with integer.
     */
    @Test
    public final void testConstructorInt() {

        int i = 0;
        int j = 999;
        int k = Integer.MAX_VALUE;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(this.constructorTest(i), this.constructorRef(i));
        assertEquals(this.constructorTest(j), this.constructorRef(j));
        assertEquals(this.constructorTest(k), this.constructorRef(k));
    }

    /**
     * Test default constructor with string.
     */
    @Test
    public final void testConstructorString() {

        String i = "0";
        String j = "1";
        String k = "999";

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(this.constructorTest(i), this.constructorRef(i));
        assertEquals(this.constructorTest(j), this.constructorRef(j));
        assertEquals(this.constructorTest(k), this.constructorRef(k));
    }

    /**
     * Test default constructor with NaturalNumber.
     */
    @Test
    public final void testConstructorNN() {

        NaturalNumber i = new NaturalNumber1L();
        NaturalNumber j = new NaturalNumber1L(0);
        NaturalNumber k = new NaturalNumber1L(1);
        NaturalNumber l = new NaturalNumber1L(Integer.MAX_VALUE);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(this.constructorTest(i), this.constructorRef(i));
        assertEquals(this.constructorTest(j), this.constructorRef(j));
        assertEquals(this.constructorTest(k), this.constructorRef(k));
        assertEquals(this.constructorTest(l), this.constructorRef(l));
    }

    /**
     * Test {@code multiplyBy10} using NN 0 multiplyby10 adding 0,1,9,0
     * accordingly.
     */
    @Test
    public final void testMultiplyBy10() {
        /*
         * Set up variables
         */
        int i = 0;
        int j = 1;
        int k = 9;
        NaturalNumber n = new NaturalNumber3();
        /*
         * test method with NN 0 adding 0
         */
        n.multiplyBy10(i);
        NaturalNumber nExpected = new NaturalNumber3(0);
        assertEquals(nExpected, n);
        /*
         * test method with NN 0 adding 1
         */
        n.multiplyBy10(j);
        nExpected = new NaturalNumber3(1);
        assertEquals(nExpected, n);
        /*
         * test method with NN 1 adding 9
         */
        n.multiplyBy10(k);
        nExpected = new NaturalNumber3(19);
        assertEquals(nExpected, n);
        /*
         * test method with NN 19 adding 0
         */
        n.multiplyBy10(i);
        nExpected = new NaturalNumber3(190);
        assertEquals(nExpected, n);
    }

    /**
     * Test {@code divideBy10} using NN 0 multiplyby10 adding 0,1,9,0
     * accordingly.
     */
    @Test
    public final void testDivideBy10() {
        /*
         * Set up variables
         */
        int i = 0;
        int iExpected = 0;

        NaturalNumber n = new NaturalNumber3(910);
        /*
         * test method with NN 910
         */
        i = n.divideBy10();

        NaturalNumber nExpected = new NaturalNumber3(91);
        iExpected = 0;
        assertEquals(nExpected, n);
        assertEquals(iExpected, i);
        /*
         * test method with NN 91
         */
        i = n.divideBy10();

        nExpected = new NaturalNumber3(9);
        iExpected = 1;
        assertEquals(nExpected, n);
        assertEquals(iExpected, i);
        /*
         * test method with NN 9
         */
        i = n.divideBy10();

        nExpected = new NaturalNumber3(0);
        iExpected = 9;
        assertEquals(nExpected, n);
        assertEquals(iExpected, i);
        /*
         * test method with NN 19 adding 0
         */
        i = n.divideBy10();

        nExpected = new NaturalNumber3(0);
        iExpected = 0;
        assertEquals(nExpected, n);
        assertEquals(iExpected, i);
    }

    /**
     * Test {@code isZero} using NN=0,1,99.
     */
    @Test
    public final void testIsZero() {
        /*
         * Test when n=0
         */
        NaturalNumber n = new NaturalNumber3();
        assertTrue(n.isZero());
        /*
         * Test when n=1
         */
        n = new NaturalNumber3(1);
        assertTrue(!n.isZero());
        /*
         * Test when n=99
         */
        n = new NaturalNumber3(99);
        assertTrue(!n.isZero());

    }
}
