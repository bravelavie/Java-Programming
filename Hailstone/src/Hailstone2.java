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
     * Put a short phrase describing the static method myMethod here.
     */
    private static void generateSeries(int n, SimpleWriter out) {
        /*
         * Put your code for myMethod here
         */
        out.print(n);
        int i = 1;
        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            out.print(", " + n);
            i++;
        }
        out.println();
        out.print("length of the series is " + i);
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
        out.print("Please enter a positive integer: ");
        int startValue = in.nextInteger();
        generateSeries(startValue, out);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
