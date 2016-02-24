import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class GlossaryTest {
    /*
     * Tests of outputDefinition method
     */
    @Test
    public void outputDefinitionTestEmptySet() {
        Set<String> terms = new Set1L();
        String definition = "no term exist";
        String out = Glossary.outputDefinition(terms, definition);
        assertEquals(out, "no term exist");
    }

    @Test
    public void outputDefinitionTestEmptyString() {
        Set<String> terms = new Set1L();
        String definition = "";
        String out = Glossary.outputDefinition(terms, definition);
        assertEquals(out, "");
    }

    @Test
    public void outputDefinitionTesthasNoTerm() {
        Set<String> terms = new Set1L();
        terms.add("a");
        terms.add("b");
        terms.add("c");
        String definition = "No term included.";
        String out = Glossary.outputDefinition(terms, definition);
        assertEquals(out, "No term included.");
    }

    @Test
    public void outputDefinitionTesthasHasTerm() {
        Set<String> terms = new Set1L();
        terms.add("a");
        terms.add("b");
        terms.add("c");
        String definition = "a is in";
        String out = Glossary.outputDefinition(terms, definition);
        assertEquals(out, "<a href=\"a.html\">a</a> is in");
    }

    @Test
    public void outputDefinitionTesthasHasMultipleTermWithSeperator() {
        Set<String> terms = new Set1L();
        terms.add("a");
        terms.add("b");
        terms.add("c");
        String definition = "String contains a, also b";
        String out = Glossary.outputDefinition(terms, definition);
        assertEquals(out,
                "String contains <a href=\"a.html\">a</a>, also <a href=\"b.html\">b</a>");
    }

    /*
     * Tests of indexAndGlossary method
     */

    @Test
    public void indexAndGlossaryTestOneTerm() {
        SimpleReader file = new SimpleReader1L("data/test1.txt");
        Set<String> terms = new Set1L<>();
        Queue<String> index = new Queue1LSort1();
        Map<String, String> glossary = new Map1L<>();
        Glossary.indexAndGlossary(index, terms, glossary, file);
        assertTrue(terms.size() == 1);
        assertTrue(index.length() == 1);
        assertTrue(glossary.size() == 1);
        assertEquals(terms.removeAny(), "meaning");
        assertEquals(index.dequeue(), "meaning");
        assertEquals(
                glossary.key("something that one wishes to convey, especially by language "),
                "meaning");
        assertEquals(glossary.value("meaning"),
                "something that one wishes to convey, especially by language ");
    }

    @Test
    public void indexAndGlossaryEmpty() {
        SimpleReader file = new SimpleReader1L("data/test2.txt");
        Set<String> terms = new Set1L<>();
        Queue<String> index = new Queue1LSort1();
        Map<String, String> glossary = new Map1L<>();
        Glossary.indexAndGlossary(index, terms, glossary, file);
        assertTrue(terms.size() == 0);
        assertTrue(index.length() == 0);
        assertTrue(glossary.size() == 0);
    }

    @Test
    public void indexAndGlossaryMultiLineDefinition() {
        SimpleReader file = new SimpleReader1L("data/test3.txt");
        Set<String> terms = new Set1L<>();
        Queue<String> index = new Queue1LSort1();
        Map<String, String> glossary = new Map1L<>();
        Glossary.indexAndGlossary(index, terms, glossary, file);
        assertTrue(terms.size() == 1);
        assertTrue(index.length() == 1);
        assertTrue(glossary.size() == 1);
        assertEquals(terms.removeAny(), "meaning");
        assertEquals(index.dequeue(), "meaning");
        assertEquals(
                glossary.key("something that one wishes to convey, especially by language "),
                "meaning");
        assertEquals(glossary.value("meaning"),
                "something that one wishes to convey, especially by language ");
    }

    @Test
    public void indexAndGlossaryMultiTerms() {
        SimpleReader file = new SimpleReader1L("data/test4.txt");
        Set<String> terms = new Set1L<>();
        Queue<String> index = new Queue1LSort1();
        Map<String, String> glossary = new Map1L<>();
        Glossary.indexAndGlossary(index, terms, glossary, file);
        assertTrue(terms.size() == 2);
        assertTrue(index.length() == 2);
        assertTrue(glossary.size() == 2);
        assertEquals(index.dequeue(), "meaning");
        assertEquals(index.dequeue(), "term");
        assertEquals(
                glossary.key("something that one wishes to convey, especially by language "),
                "meaning");
        assertEquals(glossary.value("meaning"),
                "something that one wishes to convey, especially by language ");
        assertEquals(glossary.key("a word whose definition is in a glossary "),
                "term");
        assertEquals(glossary.value("term"),
                "a word whose definition is in a glossary ");
    }

    /*
     * Method generateElements and nextWordOrSeparator have been tested in
     * previous lab. Also some simple output HTML method are not tested
     */
}
