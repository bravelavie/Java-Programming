import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program counts word occurrences in a given input file and outputs an
 * HTML document with a table of the words and counts listed in alphabetical
 * order��
 *
 * @author Kun Liu
 *
 */
public final class WordCounter {

    /**
     * Compare {@code String}s in lexicographic order with case insensitive.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1, o2);
        }
    }

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file.
     *
     * @param fileName
     *            the input file name as string
     * @param out
     *            the output stream
     * @updates out.content
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */

    private static void outputHeader(String fileName, SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Words Counted in " + fileName + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Words Counted in " + fileName + "</h2>");
        out.println("<hr>");
        out.println("<table border=\"1\">");
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<th>Words</th>");
        out.println("<th>Counts</th>");
        out.println("</tr>");
    }

    /**
     * Processes one word count item and outputs one table row.
     *
     * @param word
     *            the term in the glossary
     * @param count
     *            the count of the word
     * @param out
     *            the output stream
     * @updates out.content
     * @requires <pre>
     * out.is_open
     * </pre>
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with the word and the count of the word in given file]
     * </pre>
     */
    private static void processWordCountItem(String word, int count,
            SimpleWriter out) {
        out.println("<tr>");
        out.println("<td>" + word + "</td>");
        out.println("<td>" + count + "</td>");
        out.println("</tr>");
    }

    /**
     * Outputs the "closing" tags in the HTML file. These are the expected
     * elements generated by this method:
     *
     * </tbody></table></body></html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</tbody>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Process input file and put all words by updating a given set, also update
     * given map with both words and their counts.
     *
     * @param inputFile
     *            the input stream
     * @param words
     *            the set for all words
     * @param countMap
     *            the word map with each word and its count
     * @updates words, countMap
     * @ensures words set contain all words in the file, and countMap contain
     *          all pairs of words and their counts
     */

    public static void processFile(Set<String> words,
            Map<String, Integer> countMap, SimpleReader inputFile) {

        String line;
        final String separatorStr = " \t,-,. ";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorStr, separatorSet);

        while (!inputFile.atEOS()) {
            line = inputFile.nextLine();
            int position = 0;
            while (position < line.length()) {
                String token = nextWordOrSeparator(line, position, separatorSet);
                if (!separatorSet.contains(token.charAt(0))) {
                    if (!countMap.hasKey(token)) {
                        countMap.add(token, 1);
                    } else {
                        int oldV = countMap.value(token);
                        int newV = oldV + 1;
                        countMap.replaceValue(token, newV);
                    }
                    if (!words.contains(token)) {
                        words.add(token);
                    }
                }
                position += token.length();
            }
        }
    }

    /**
     * Create a queue from a given set with arbitrary order.
     *
     * @param wordSet
     *            the input set with has all words
     * @return queue with all elements in the set in arbitrary order
     * @ensures output queue contain all elements in the set
     *
     */

    public static Queue<String> createQueue(Set<String> wordSet) {
        Queue<String> wordQueue = new Queue1L<>();
        Set<String> temp = wordSet.newInstance();
        temp.transferFrom(wordSet);
        while (temp.size() > 0) {
            String word = temp.removeAny();
            wordQueue.enqueue(word);
            wordSet.add(word);
        }
        return wordQueue;
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces strSet
     * @ensures strSet = entries(str)
     */

    private static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";

        Set<Character> temp = strSet.newInstance();
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            if (!temp.contains(current)) {
                temp.add(current);
            }
        }
        strSet.transferFrom(temp);
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        String result = "";
        String sub = text.substring(position, text.length());
        if (separators.contains(sub.charAt(0))) {
            for (int j = 0; j < sub.length(); j++) {
                if (separators.contains(sub.charAt(j))) {
                    result += sub.charAt(j);
                } else {
                    break;
                }
            }
        } else {
            for (int i = 0; i < sub.length(); i++) {
                if (!separators.contains(sub.charAt(i))) {
                    result += sub.charAt(i);
                } else {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Get input file name and open input stream
         */
        out.print("Enter an input file name: ");
        String fileName = in.nextLine();
        SimpleReader file = new SimpleReader1L(fileName);

        out.print("Enter an output file name: ");
        String outFileName = in.nextLine();

        Set<String> words = new Set1L<>();
        Map<String, Integer> wordCount = new Map1L<>();
        /*
         * Get set and map for words and word counts
         */
        processFile(words, wordCount, file);
        Queue<String> wordQueue = createQueue(words);
        /*
         * Sort queue in alphabetical order
         */
        Comparator<String> alphabetical = new StringLT();
        wordQueue.sort(alphabetical);

        /*
         * create HTML file to start output
         */
        SimpleWriter fileOut = new SimpleWriter1L(outFileName);
        /*
         * print header to output file
         */
        outputHeader(fileName, fileOut);

        /*
         * print table rows of word counts
         */
        while (wordQueue.length() > 0) {
            String nextWord = wordQueue.dequeue();
            int nextWordCount = wordCount.value(nextWord);
            processWordCountItem(nextWord, nextWordCount, fileOut);
        }

        /*
         * print footer to output file
         */
        outputFooter(fileOut);

        /*
         * Close input and output streams
         */
        file.close();
        fileOut.close();
        in.close();
        out.close();
    }
}
