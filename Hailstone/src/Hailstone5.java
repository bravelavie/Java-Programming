import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Hailstone5 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Hailstone5() {
    }

    /**
     * Generates and outputs the Hailstone series starting with the given
     * integer.
     *
     * @param n
     *            the starting integer
     * @param out
     *            the output stream
     */
    private static void generateSeries(int n, SimpleWriter out) {
        /*
         * Put your code for myMethod here
         */
        out.print(n);
        int i = 1;
        int max = 0;
        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;

            }
            if (max < n) {
                max = n;
            }
            out.print(", " + n);
            i++;
        }
        out.println();
        out.println("length of the series is " + i);
        out.println("maximum value of the series is " + max);
    }

    /**
     * Repeatedly asks the user for a positive integer until the user enters
     * one. Returns the positive integer.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive integer entered by the user
     */
    private static int getPositiveInteger(SimpleReader in, SimpleWriter out) {

        String startValue = in.nextLine();
        while (!FormatChecker.canParseInt(startValue)
                || Integer.parseInt(startValue) <= 0) {
            out.print("Please enter a positive integer: ");
            startValue = in.nextLine();
        }

        return Integer.parseInt(startValue);
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

        out.print("Please enter a positive integer: ");
        int startInt = getPositiveInteger(in, out);
        generateSeries(startInt, out);
        out.println("Do you want to calculate another series? ");
        while (in.nextLine().equals("y")) {
            out.print("Please enter a positive integer: ");
            startInt = getPositiveInteger(in, out);
            generateSeries(startInt, out);
            out.println("Do you want to calculate another series? ");
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }
}
