import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class StringReassemblyTest {
    /*
     * Tests of combination method
     */
    @Test
    public void testCombinationEmpty() {
        String str1 = "";
        String str2 = "";
        int overlap = 0;
        String out = StringReassembly.combination(str1, str2, overlap);
        assertEquals(out, "");
    }

    @Test
    public void testCombinationNoOverlap() {
        String str1 = "abc";
        String str2 = "def";
        int overlap = 0;
        String out = StringReassembly.combination(str1, str2, overlap);
        assertEquals(out, "abcdef");
    }

    @Test
    public void testCombinationOneOverlap() {
        String str1 = "abc";
        String str2 = "cdef";
        int overlap = 1;
        String out = StringReassembly.combination(str1, str2, overlap);
        assertEquals(out, "abcdef");
    }

    @Test
    public void testCombinationTotalOverlap() {
        String str1 = "abc";
        String str2 = "abc";
        int overlap = 3;
        String out = StringReassembly.combination(str1, str2, overlap);
        assertEquals(out, "abc");
    }

    @Test
    public void testCombinationLongOverlap() {
        String str1 = "The idea of reassembling a long sequence from many overlapping fragments";
        String str2 = "reassembling a long sequence from many overlapping fragments of the whole is not new.";
        int overlap = StringReassembly.overlap(str1, str2);
        String out = StringReassembly.combination(str1, str2, overlap);
        assertEquals(
                out,
                "The idea of reassembling a long sequence from many overlapping fragments of the whole is not new.");
    }

    /*
     * Tests of addToSetAvoidingSubstrings
     */

    @Test
    public void testAddToSetAvoidingSubstringsEmpty() {
        Set<String> strSet = new Set1L<>();
        String str = "";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertTrue(strSet.contains(str));
    }

    @Test
    public void testAddToSetAvoidingSubstringsRoutine() {
        Set<String> strSet = new Set1L<>();
        strSet.add("abc");
        strSet.add("bcd");
        String str = "cde";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertTrue(strSet.contains(str));
    }

    @Test
    public void testAddToSetAvoidingSubstringsAddSubstring() {
        Set<String> strSet = new Set1L<>();
        strSet.add("abc");
        strSet.add("bcd");
        String str = "cd";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertTrue(!strSet.contains(str));
    }

    @Test
    public void testAddToSetAvoidingSubstringsHasSubstring() {
        Set<String> strSet = new Set1L<>();
        strSet.add("abc");
        strSet.add("bcd");
        String str = "bcde";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertTrue(strSet.contains(str));
        assertTrue(!strSet.contains("bcd"));
    }

    @Test
    public void testAddToSetAvoidingSubstringsHasMultipleSubstring() {
        Set<String> strSet = new Set1L<>();
        strSet.add("abc");
        strSet.add("bcd");
        strSet.add("cde");
        String str = "abcde";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertTrue(strSet.contains(str));
        assertTrue(!strSet.contains("abc"));
        assertTrue(!strSet.contains("bcd"));
        assertTrue(!strSet.contains("cde"));
    }

    /*
     * Tests of linesFromInput (using *.txt files in the data folder)
     */
    @Test
    public void testLinesFromInputEmpty() {
        String inputFileName = "data/test1.txt";
        SimpleReader inFile = new SimpleReader1L(inputFileName);
        Set<String> test = StringReassembly.linesFromInput(inFile);
        Set<String> ref = test.newInstance();
        assertEquals(test, ref);
    }

    @Test
    public void testLinesFromInputRoutine() {
        String inputFileName = "data/test2.txt";
        SimpleReader inFile = new SimpleReader1L(inputFileName);
        Set<String> test = StringReassembly.linesFromInput(inFile);
        Set<String> ref = test.newInstance();
        ref.add("abc");
        ref.add("bcd");
        ref.add("cde");
        assertEquals(test, ref);
    }

    @Test
    public void testLinesFromInputSubstring() {
        String inputFileName = "data/test3.txt";
        SimpleReader inFile = new SimpleReader1L(inputFileName);
        Set<String> test = StringReassembly.linesFromInput(inFile);
        Set<String> ref = test.newInstance();
        ref.add("abc");
        ref.add("bcd");
        assertEquals(ref, test);
    }

    @Test
    public void testLinesFromInputMultiSubstring() {
        String inputFileName = "data/test4.txt";
        SimpleReader inFile = new SimpleReader1L(inputFileName);
        Set<String> test = StringReassembly.linesFromInput(inFile);
        Set<String> ref = test.newInstance();
        ref.add("abcdef");
        assertEquals(ref, test);
    }

    @Test
    public void testLinesFromInputMultiCheers() {
        String inputFileName = "data/cheer.txt";
        SimpleReader inFile = new SimpleReader1L(inputFileName);
        Set<String> test = StringReassembly.linesFromInput(inFile);
        Set<String> ref = test.newInstance();
        ref.add("Bucks -- Beat");
        ref.add("Go Bucks");
        ref.add("Beat Mich");
        ref.add("Michigan~");
        ref.add("o Bucks -- B");
        assertEquals(ref, test);
    }

}
