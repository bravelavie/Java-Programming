import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * This program generates a tag cloud from a given input text file.
 *
 * @author Kun Liu, Son Le
 *
 */
public final class TagCloudGenerator {
    /**
     * Minimum count of the words.
     */
    private static int minCount = Integer.MIN_VALUE;

    /**
     * Maximum count of the words.
     */
    private static int maxCount = Integer.MAX_VALUE;

    /**
     * Maximum font size allowed.
     */
    public static final int MAX_FONT_SIZE = 48;

    /**
     * Minimum font size allowed.
     */
    public static final int MIN_FONT_SIZE = 11;
    /**
     * The separators.
     */
    private static final String SEPARATORS = " \t\n\r,-.!?[]';:/()~!@#$%^&*()`_+=\\|\"<>{}";

    /**
     * Compare {@code Map.Pair}s in alphabetical order.
     */
    private static Comparator<Map.Pair<String, Integer>> ALPHABETICAL = new Comparator<Map.Pair<String, Integer>>() {
        @Override
        public int compare(Map.Pair<String, Integer> p1,
                Map.Pair<String, Integer> p2) {
            return p1.key().toLowerCase().compareTo(p2.key().toLowerCase());
        }
    };

    /**
     * Compare {@code Map.Pair}s in order of count frequency.
     */
    private static Comparator<Map.Pair<String, Integer>> COUNT = new Comparator<Map.Pair<String, Integer>>() {
        @Override
        public int compare(Map.Pair<String, Integer> p1,
                Map.Pair<String, Integer> p2) {
            return p2.value() - p1.value();
        }
    };

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * Return the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
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

    private static String nextWordOrSeparator(String text, int position) {
        assert text != null : "Violation of: text is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        String result = "";
        String sub = text.substring(position, text.length());
        if (SEPARATORS.contains(String.valueOf(sub.charAt(0)))) {
            for (int j = 0; j < sub.length(); j++) {
                if (SEPARATORS.contains(String.valueOf(sub.charAt(j)))) {
                    result += sub.charAt(j);
                } else {
                    break;
                }
            }
        } else {
            for (int i = 0; i < sub.length(); i++) {
                if (!SEPARATORS.contains(String.valueOf(sub.charAt(i)))) {
                    result += sub.charAt(i);
                } else {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Count the times of appearance of each word from the input file.
     *
     * @param inputFile
     *            the input file.
     * @param map
     *            map containing all the words and their counts.
     * @updates map
     */
    private static void wordCount(SimpleReader inputFile,
            Map<String, Integer> map) {
        assert inputFile != null : "Violation of: out is not null";
        assert inputFile.isOpen() : "Violation of: out.is_open";

        while (!inputFile.atEOS()) {
            String line = inputFile.nextLine();
            int position = 0;
            while (position < line.length()) {
                String token = nextWordOrSeparator(line, position)
                        .toLowerCase();
                if (!SEPARATORS.contains(String.valueOf(token.charAt(0)))) {
                    if (map.hasKey(token)) {
                        map.replaceValue(token, map.value(token) + 1);
                    } else {
                        map.add(token, 1);
                    }
                }
                position += token.length();
            }
        }
    }

    /**
     * Calculate the font size for a certain word.
     *
     * @param count
     *            the count of the word to be sized.
     * @return the font size for the word.
     */
    private static int calculateFontSize(int count) {
        int size = 0;
        if (count == maxCount) {
            size = MAX_FONT_SIZE;
        } else if (count == minCount) {
            size = MIN_FONT_SIZE;
        } else {
            double ratio = 1.0 * (count - minCount) / (maxCount - minCount);
            int range = MAX_FONT_SIZE - MIN_FONT_SIZE;
            size = (int) (ratio * range + MIN_FONT_SIZE);
        }
        return size;
    }

    /**
     * Output the "opening" tags in the generated HTML file.
     *
     * @param out
     *            the output stream
     * @param fileName
     *            the file name which all the words come from
     * @param cloudSize
     *            the size of cloud chosen by user
     */
    private static void outputHeader(SimpleWriter out, String fileName,
            int cloudSize) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("<html>");
        out.println("<head>");
        out.print("<title>");
        out.print("Top " + cloudSize + " words in " + fileName);
        out.println("</title>");
        out.println("<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.print("</head>");
        out.println("<body>");
        out.print("<h2>Top " + cloudSize + " words in " + fileName + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");
    }

    /**
     * Output one word with its size and count as one table row.
     *
     * @param out
     *            the output stream
     * @param pair
     *            the pair containing the word string and the number of count
     */
    private static void outputWord(SimpleWriter out,
            Map.Pair<String, Integer> pair) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.print("<span style=\"cursor:default\" class=\"f");
        out.print(calculateFontSize(pair.value()));
        out.println("\" title=\"count: " + pair.value() + "\">" + pair.key()
                + "</span>");
    }

    /**
     * Output the "closing" tags in the generated HTML file.
     *
     * @param out
     *            the output stream
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
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

        Map<String, Integer> wordMap = new Map1L<>();

        /*
         * Take input file.
         */
        out.print("Enter the name for the input file: ");
        String inputFileName = in.nextLine();

        /*
         * Name output file.
         */
        out.print("Enter the name for the output file: ");
        String outputFileName = in.nextLine();

        /*
         * Input cloud size.
         */
        out.print("Enter the size of the cloud: ");
        int cloudSize = in.nextInteger();

        SimpleReader file = new SimpleReader1L(inputFileName);

        /*
         * count all words from the input file.
         */
        wordCount(file, wordMap);
        file.close();

        /*
         * cloud size cannot exceed word map size.
         */
        if (wordMap.size() < cloudSize) {
            cloudSize = wordMap.size();
        }

        /*
         * Build two sorting machines for word map sorting.
         */
        SortingMachine<Map.Pair<String, Integer>> countSort = new SortingMachine1L<>(
                COUNT);
        SortingMachine<Map.Pair<String, Integer>> alphabetSort = new SortingMachine1L<>(
                ALPHABETICAL);

        /*
         * Sort the word map by word count.
         */
        for (Map.Pair<String, Integer> pair : wordMap) {
            countSort.add(pair);
        }

        wordMap.clear();
        countSort.changeToExtractionMode();

        for (int i = 0; i < cloudSize; i++) {
            Map.Pair<String, Integer> pair = countSort.removeFirst();
            wordMap.add(pair.key(), pair.value());
            if (i == 0) {
                maxCount = pair.value();
            }
            if (i == cloudSize - 1) {
                minCount = pair.value();
            }
        }

        /*
         * Sort the word map by alphabetical order.
         */
        for (Map.Pair<String, Integer> pair : wordMap) {
            alphabetSort.add(pair);
        }

        /*
         * Write output into output file.
         */
        SimpleWriter outputFile = new SimpleWriter1L(outputFileName);
        outputHeader(outputFile, inputFileName, cloudSize);
        alphabetSort.changeToExtractionMode();
        for (int i = 0; i < cloudSize; i++) {
            Map.Pair<String, Integer> pair = alphabetSort.removeFirst();
            outputWord(outputFile, pair);
        }
        outputFooter(outputFile);
        outputFile.close();

        in.close();
        out.close();
    }
}