import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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
    private static Comparator<Map.Entry<String, Integer>> ALPHABETICAL = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> p1,
                Map.Entry<String, Integer> p2) {
            return p1.getKey().toLowerCase()
                    .compareTo(p2.getKey().toLowerCase());
        }
    };

    /**
     * Compare {@code Map.Pair}s in order of count frequency.
     */
    private static Comparator<Map.Entry<String, Integer>> COUNT = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> p1,
                Map.Entry<String, Integer> p2) {
            return p2.getValue() - p1.getValue();
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
     * @ensures
     *
     *          <pre>
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
    private static void wordCount(BufferedReader inputFile,
            Map<String, Integer> map) {
        assert inputFile != null : "Violation of: out is not null";

        try {
            assert inputFile.ready() : "Violation of: out.is_open";
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            while ((line = inputFile.readLine()) != null) {
                int position = 0;
                while (position < line.length()) {
                    String token = nextWordOrSeparator(line, position)
                            .toLowerCase();
                    if (!SEPARATORS.contains(String.valueOf(token.charAt(0)))) {
                        if (map.containsKey(token)) {
                            map.replace(token, map.get(token) + 1);
                        } else {
                            map.put(token, 1);
                        }
                    }
                    position += token.length();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    private static void outputHeader(PrintWriter out, String fileName,
            int cloudSize) {
        assert out != null : "Violation of: out is not null";

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
    private static void outputWord(PrintWriter out,
            Map.Entry<String, Integer> pair) {
        assert out != null : "Violation of: out is not null";

        out.print("<span style=\"cursor:default\" class=\"f");
        out.print(calculateFontSize(pair.getValue()));
        out.println("\" title=\"count: " + pair.getValue() + "\">"
                + pair.getKey() + "</span>");
    }

    /**
     * Output the "closing" tags in the generated HTML file.
     *
     * @param out
     *            the output stream
     */
    private static void outputFooter(PrintWriter out) {
        assert out != null : "Violation of: out is not null";

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
        /*
         * Take input file.
         */
        System.out.print("Enter the name for the input file: ");

        Scanner in = new Scanner(System.in);
        String inputFileName = in.nextLine();
        /*
         * Name output file.
         */
        System.out.print("Enter the name for the output file: ");
        String outputFileName = in.nextLine();
        /*
         * Input cloud size.
         */
        int cloudSize = 0;
        System.out.print("Enter the size of the cloud: ");
        cloudSize = in.nextInt();
        while (cloudSize <= 0) {
            System.out.print("Enter the size of the cloud: ");
            cloudSize = in.nextInt();
        }

        Map<String, Integer> wordMap = new TreeMap<String, Integer>();
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader(inputFileName));
            /*
             * count all words from the input file.
             */
            wordCount(input, wordMap);
            input.close();
            /*
             * cloud size cannot exceed word map size.
             */
        } catch (IOException e) {
            System.err.println("Error Openning File");
            in.close();
            return;
        }

        if (wordMap.size() < cloudSize) {
            cloudSize = wordMap.size();
        }
        /*
         * Build two sorting machines for word map sorting.
         */
        ArrayList<Map.Entry<String, Integer>> countSort = new ArrayList<>();

        ArrayList<Map.Entry<String, Integer>> alphabetSort = new ArrayList<>();
        /*
         * Sort the word map by word count.
         */
        for (Map.Entry<String, Integer> pair : wordMap.entrySet()) {
            countSort.add(pair);
        }
        countSort.sort(COUNT);
        wordMap.clear();
        for (int i = 0; i < cloudSize; i++) {
            Map.Entry<String, Integer> pair = countSort.get(i);
            wordMap.put(pair.getKey(), pair.getValue());
            if (i == 0) {
                maxCount = pair.getValue();
            }
            if (i == cloudSize - 1) {
                minCount = pair.getValue();
            }
        }

        /*
         * Sort the word map by alphabetical order.
         */
        for (Map.Entry<String, Integer> pair : wordMap.entrySet()) {
            alphabetSort.add(pair);
        }
        alphabetSort.sort(ALPHABETICAL);

        /*
         * Write output into output file.
         */

        try {
            PrintWriter write = new PrintWriter(new BufferedWriter(
                    new FileWriter(outputFileName)));
            outputHeader(write, inputFileName, cloudSize);
            for (int i = 0; i < cloudSize; i++) {
                Map.Entry<String, Integer> pair = alphabetSort.get(i);
                outputWord(write, pair);
            }
            outputFooter(write);
            System.out.println("Tag Cloud Generated Successfully");
            write.close();
        } catch (IOException io) {
            System.err.println("Error Generating Output File");
            in.close();
            return;
        }
        in.close();
    }
}