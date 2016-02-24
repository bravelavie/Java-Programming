import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Hailstone2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Hailstone2() {
    }

    /**
     * Generates and outputs the Hailstone series starting with the given
     * {@code NaturalNumber} and output the length of the series
     *
     * @param n
     *            the starting natural number
     * @param out
     *            the output stream
     * @updates out.content
     * @requires n > 0 and out.is_open
     * @ensures out.content = #out.content * [the Hailstone series starting with
     *          n]
     */
    private static void generateSeries(NaturalNumber n, SimpleWriter out) {

        out.print(n);
        int length = 1;
        NaturalNumber one = new NaturalNumber1L(1);
        NaturalNumber two = new NaturalNumber1L(2);
        NaturalNumber three = new NaturalNumber1L(3);
        NaturalNumber m = new NaturalNumber1L();
        m.copyFrom(n);
        while (n.compareTo(one) != 0) {
            if (m.divide(two).isZero()) {
                n.divide(two);
            } else {
                n.multiply(three);
                n.add(one);
            }
            m.copyFrom(n);
            out.print(", " + n);
            length++;
        }
        out.println();
        out.println("length of the series is: " + length);
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
         * Put your main program code here; it may call myMethod as shown
         */
        out.print("Please enter a integer: ");
        int input = in.nextInteger();
        NaturalNumber startValue = new NaturalNumber1L(input);
        generateSeries(startValue, out);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
