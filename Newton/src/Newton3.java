import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Compute the square root of a non-negative number using Newton iteration.
 *
 * @author Kun Liu
 *
 */
public final class Newton3 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton3() {
    }

    /**
     * Computes estimate of square root of x to within relative error.
     *
     * @param x
     *            non-negative number to compute square root of
     * @param epsilon
     *            relative error that can be allowed
     * @return estimate of square root
     */
    private static double sqrt(double x, double epsilon) {

        double r = x; //set initial guess as x
        /*
         * Treat x=0 as special case and calculate square root as 0
         */
        if (x == 0) {
            r = x;
        } else {
            /*
             * Construct a loop converging to x^1/2 until relative error
             * tolerance reached
             */
            while (Math.abs(r * r - x) / x >= epsilon * epsilon) {

                r = (r + x / r) / 2;

            }
        }
        /* return sqrt after iteration */
        return r;
    }

    /**
     * Main method.
     *
     * @param args
     *            repeatedly ask for non-negative number and relative error and
     *            call sqrt method
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        do {
            /*
             * Ask user to input value for calculation
             */
            out.print("Please enter a non-negative number: ");
            double inputDouble = in.nextDouble();
            /*
             * Ask user for epsilon (error tolerance)
             */
            out.print("Please enter value of relative error epsilon: ");
            double e = in.nextDouble();
            /*
             * Call method sqrt to compute square root and print
             */
            out.println("The square root of input value is "
                    + sqrt(inputDouble, e));
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
