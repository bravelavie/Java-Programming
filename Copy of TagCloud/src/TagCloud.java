import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Tag cloud program which asks users to input a text file then print oout an
 * output in html
 *
 * @author son le and kun liu
 *
 */

public final class TagCloud {

    private final static int MAXFONTSIZE = 48;

    /**
     * output headers and tags.
     *
     * @param out
     *            SimpleWriter
     * @param name
     *            the name of input file
     * @updates out is open
     * @requires out is open
     * @ensures out.content = *out.content+ Headers Tags
     */
    private static void outputHeaders(SimpleWriter out, int number,
            String inName) {
        out.println("<html>");
        out.println("<head>");
        out.print("<title>");
        out.print("Top " + number + " in " + inName);
        out.println("</title>");
        out.print("<link href=");
        out.print("\"");
        out.print("tagcloud.css");
        out.print("\"");
        out.print(" rel=");
        out.print("\"");
        out.print("stylesheet");
        out.print("\"");
        out.print(" type=");
        out.print("\"");
        out.print("text/css");
        out.print("\"");
        out.println(">");
        out.println("</head>");
        out.println("<body>");
        out.print("<h2>");
        out.print("Top " + number + " in " + inName);
        out.println("</h2>");
        out.println("<hr></hr>");
        out.print("<div class=");
        out.print("\"");
        out.print("cdiv");
        out.print("\"");
        out.println(">");
        out.print("<p class =");
        out.print("\"");
        out.print("cbox");
        out.print("\"");
        out.println(">");
    }

    /**
     * Compare {@code String}s in alphabetical order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
        /**
         * Compare {@code Occurrence}s in alphabetical order.
         */
    }

    private static class IntegerLT implements Comparator<Integer> {
        @Override
        public int compare(Integer num1, Integer num2) {
            if (num1 < num2) {
                return 1;
            } else if (num1 > num2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * return a string, either a separator or a word
     *
     * @param text
     *            the given long string which contains separators and words
     * @param position
     *            the starting index
     * @param separators
     *            a Set <Character> which contains separators
     * @return the first word or a separator
     * @requires 0 <= position < |text|
     * @ensures nextWordOrSeparator = a string of separators or a word
     *
     */
    public static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        // TODO - fill in body
        String result = "";

        // Not taking the last character which not either a separator or a word
        if (separators.contains(text.charAt(position))) {
            boolean nextInSet = true;
            for (int i = position; i < text.length() && nextInSet; i++) {
                if (separators.contains(text.charAt(i))) {
                    result += text.charAt(i);
                } else {
                    nextInSet = false;
                }
            }
        } else {
            boolean nextNotInSet = true;
            for (int j = position; j < text.length() && nextNotInSet; j++) {
                if (!separators.contains(text.charAt(j))) {
                    result += text.charAt(j);
                } else {
                    nextNotInSet = false;
                }
            }
        }
        return result;
    }

    /**
     * return the min occurences of the word
     *
     * @param map
     *            the map contains occurrences of the word
     * @return the min occurrence
     * @ensures the min value of the map
     */
    private static int minOcurrence(Map<String, Integer> map) {
        int min = Integer.MAX_VALUE;
        Map<String, Integer> temp = map.newInstance();
        temp.transferFrom(map);
        while (temp.size() > 0) {
            Map.Pair<String, Integer> pair = temp.removeAny();
            if (min > pair.value()) {
                min = pair.value();
            }
            map.add(pair.key(), pair.value());
        }
        return min;
    }

    /**
     * return the max occurences of the word
     *
     * @param map
     *            the map contains occurrences of the word
     * @return the max occurrence
     * @ensures the max value of the map
     */
    private static int maxOcurrence(Map<String, Integer> map) {
        int max = Integer.MIN_VALUE;
        Map<String, Integer> temp = map.newInstance();
        temp.transferFrom(map);
        while (temp.size() > 0) {
            Map.Pair<String, Integer> pair = temp.removeAny();
            if (max < pair.value()) {
                max = pair.value();
            }
            map.add(pair.key(), pair.value());
        }
        return max;
    }

    /**
     * return the font size
     *
     * @param count
     *            the occurrences of the word
     * @requires count >0
     * @return the font size of the word
     * @ensures the font size of the word is return according to the scale
     */
    private static int fontSize(int count, int minOccurrence,
            int maxOccurrence) {
        int val = 0;
        if (count > minOccurrence && maxOccurrence > minOccurrence) {
            val = MAXFONTSIZE * (count - minOccurrence)
                    / (maxOccurrence - minOccurrence);
        } else {
            val = 1;
        }
        return val;
    }

    /**
     * output body
     *
     * @param out
     *            SimpleWriter
     * @param count
     *            the number of occurrences
     * @param size
     *            the font size for the word
     * @param name
     *            the name of input file
     * @updates out is open
     * @requires out is open
     * @ensures out.content = *out.content+ Body content
     */
    private static void outputBody(SimpleWriter out, int size, int count,
            String word) {

        out.print("<span style=");
        out.print("\"");
        out.print("cursor:default");
        out.print("\"");
        out.print(" class=");
        out.print("\"");
        out.print("f" + size);
        out.print("\"");
        out.print(" title=");
        out.print("\"");
        out.print("count: ");
        out.print(count);
        out.print("\"");
        out.print(">");
        out.println(word + "</span>");

    }

    /**
     * print out the closing tags for the output file.
     *
     * @params out the stream output
     * @requires out.isOpen
     * @ensures out.content = *out.content*closing tags
     *
     */
    private static void closingTags(SimpleWriter out) {

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

        SimpleWriter outtest = new SimpleWriter1L("test.txt");
        /*
         * Ask users for names of input and out put files, and positive integer
         */
        out.print("Enter the name of your input file: ");
        String inName = in.nextLine();
        out.print("Enter the name of your output file: ");
        String outName = in.nextLine();
        out.print("Enter a positive number: ");
        int number = in.nextInteger();
        while (number < 0) {
            out.println("Enter another positive number.");
            number = in.nextInteger();
        }

        SimpleReader inFile = new SimpleReader1L(inName);
        SimpleWriter outFile = new SimpleWriter1L(outName);

        //Arrange separators in a set
        String separatorString = " ,~!@#$%^&*()<>?{}|[];'./_+-=";
        Set<Character> separators = new Set1L();
        for (int i = 0; i < separatorString.length(); i++) {
            if (!separators.contains(separatorString.charAt(i))) {
                separators.add(separatorString.charAt(i));
            }
        }

        //Read  the file and place words and occurrences in a map
        Map<String, Integer> wordsAndOccurrences = new Map1L<String, Integer>();
        while (!inFile.atEOS()) {
            String text = inFile.nextLine();
            int position = 0;
            while (position < text.length()) {
                String nextWordsOrSeparators = nextWordOrSeparator(text,
                        position, separators);
                int occurrences = 1;
                // the word doesnt contain any special character, so its a word
                if (!separators.contains(nextWordsOrSeparators.charAt(0))) {
                    // the word may not already in the map, set its value to 1 for the first time

                    if (!wordsAndOccurrences.hasKey(nextWordsOrSeparators)) {
                        wordsAndOccurrences.add(nextWordsOrSeparators,
                                occurrences);
                    }
                    // the word may already in the map so just add 1 more to word counter
                    else {
                        Map.Pair<String, Integer> pair = wordsAndOccurrences
                                .remove(nextWordsOrSeparators);
                        wordsAndOccurrences.add(nextWordsOrSeparators,
                                pair.value() + 1);
                    }
                    outtest.println(nextWordsOrSeparators + "  ");

                }

                position += nextWordsOrSeparators.length();
            }

        }

        //print out headers
        outputHeaders(outFile, number, inName);

        //after getting the map, create sorting machine then sort it upon words' occurrences
        Comparator<Integer> comparator = new IntegerLT();
        SortingMachine<Integer> machineOccurrence = new SortingMachine1L<>(
                comparator);
        //place words into the sorting machine to sort
        Map<String, Integer> temp = wordsAndOccurrences.newInstance();
        temp.transferFrom(wordsAndOccurrences);
        while (temp.size() > 0) {
            Map.Pair<String, Integer> pair = temp.removeAny();
            machineOccurrence.add(pair.value());
            wordsAndOccurrences.add(pair.key(), pair.value());
        }
        machineOccurrence.changeToExtractionMode();
        System.out.println(machineOccurrence.toString());

        //after sort it non decreasing order, sort a certain amount of words base on the user input alphabetically
        Comparator<String> comparatorString = new StringLT();
        SortingMachine<String> machine = new SortingMachine1L<>(
                comparatorString);
        Map<String, Integer> temp1 = wordsAndOccurrences.newInstance();
        temp1.transferFrom(wordsAndOccurrences);
        for (int i = 0; i < number; i++) {
            int occurrence = machineOccurrence.removeFirst();
            String word = temp1.key(occurrence);
            machine.add(word);
            Map.Pair<String, Integer> pair = temp1.remove(word);
            wordsAndOccurrences.add(pair.key(), pair.value());
        }
        machine.changeToExtractionMode();
        //printout body
        int min = minOcurrence(wordsAndOccurrences);
        int max = maxOcurrence(wordsAndOccurrences);
        for (int j = 0; j < machine.size(); j++) {
            String str = machine.removeFirst();
            int size = fontSize(wordsAndOccurrences.value(str), min, max);
            outputBody(outFile, size, wordsAndOccurrences.value(str), str);
            wordsAndOccurrences.add(str, wordsAndOccurrences.value(str));
        }

        //printout closing tags
        closingTags(outFile);
        out.println("Done! GoodBye!");

        /*
         * Close input and output streams
         */
        inFile.close();
        outFile.close();
        in.close();
        out.close();
    }

}
