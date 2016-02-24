import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Compute the square root of a positive number using Newton iteration.
 *
 * @author Kun Liu
 *
 */
public final class Newton1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton1() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {

        double r = x; //set initial guess as x
        double epsilon = 0.0001; //set relative error tolerance
        /*
         * Construct a loop converging to x^1/2 until relative error tolerance
         * reached
         */
        while (Math.abs(r * r - x) / x >= epsilon * epsilon) {

            r = (r + x / r) / 2;

        }

        /* return sqrt after iteration */
        return r;
    }

    /**
     * Main method.
     *
     * @param args
     *            repeatedly ask for positive number and call sqrt method
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        do {
            /*
             * Ask user to input value for calculation
             */
            out.print("Please enter a positive number: ");
            double inputDouble = in.nextDouble();

            /*
             * Call method sqrt(double x) to compute square root and print
             */
            out.println("The square root of input value is "
                    + sqrt(inputDouble));
            /*
             * Ask user whether they wish to calculate another square root
             */
            out.println("Do you want to calculate another square root? ");
        } while (in.nextLine().equals("y")); //back to loop if answer "y"
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }
}
