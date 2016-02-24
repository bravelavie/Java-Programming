import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

public class NaturalNumberStaticOpsTest {

    /**
     * Test toStringWithCommas with input 0.
     */
    @Test
    public void toStringWithCommasTest1() {
        /*
         * Test boundary with n=0
         */
        NaturalNumber n = new NaturalNumber2(0);
        String s = NaturalNumberStaticOps.toStringWithCommas(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("0", s);
        assertEquals(new NaturalNumber2(0), n);
    }

    /**
     * Test toStringWithCommas with input 1.
     */
    @Test
    public void toStringWithCommasTest2() {
        /*
         * Test single digit number with n=1
         */
        NaturalNumber n = new NaturalNumber2(1);
        String s = NaturalNumberStaticOps.toStringWithCommas(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("1", s);
        assertEquals(new NaturalNumber2(1), n);
    }

    /**
     * Test toStringWithCommas with input 999.
     */
    @Test
    public void toStringWithCommasTest3() {
        /*
         * Test boundary with n=999
         */
        NaturalNumber n = new NaturalNumber2(999);
        String s = NaturalNumberStaticOps.toStringWithCommas(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("999", s);
        assertEquals(new NaturalNumber2(999), n);
    }

    /**
     * Test toStringWithCommas with input 1000.
     */
    @Test
    public void toStringWithCommasTest4() {
        /*
         * Test boundary with n=1000
         */
        NaturalNumber n = new NaturalNumber2(1000);
        String s = NaturalNumberStaticOps.toStringWithCommas(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("1,000", s);
        assertEquals(new NaturalNumber2(1000), n);
    }

    /**
     * Test toStringWithCommas with input 1234567.
     */
    @Test
    public void toStringWithCommasTest5() {
        /*
         * Test routine with n=1234567
         */
        NaturalNumber n = new NaturalNumber2(1234567);
        String s = NaturalNumberStaticOps.toStringWithCommas(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("1,234,567", s);
        assertEquals(new NaturalNumber2(1234567), n);
    }
}
