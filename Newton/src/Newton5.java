import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Compute the k-th root of a non-negative number using Newton iteration.
 *
 * @author Kun Liu
 *
 */
public final class Newton5 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton5() {
    }

    /**
     * Computes estimate of k-th root of x to within relative error.
     *
     * @param x
     *            non-negative number to compute square root of
     * @param epsilon
     *            relative error that can be allowed
     * @param k
     *            order of root
     * @return estimate of k-th root
     */
    private static double kthrt(double x, double epsilon, double k) {

        double r = x; //set initial guess as x
        /*
         * Treat x=0 as special case and calculate square root as 0
         */
        if (x == 0) {
            r = x;
        } else {
            /*
             * Construct a loop converging to x^1/k until relative error
             * tolerance reached, using Newton iteration
             */
            while (Math.abs(Math.pow(r, k) - x) / x >= epsilon * epsilon) {

                r = r - (Math.pow(r, k) - x) / (k * Math.pow(r, k - 1)); //r=r-f(r)/f'(r)

            }
        }
        /* return root after iteration */
        return r;
    }

    /**
     * Main method.
     *
     * @param args
     *            repeatedly ask for non-negative number and relative error and
     *            call kthrt method
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Ask user to input value for calculation
         */
        out.print("Please enter a non-negative number: ");
        double inputDouble = in.nextDouble();

        do {
            /*
             * Ask user for epsilon (error tolerance)
             */
            out.print("Please enter value of relative error epsilon: ");
            double e = in.nextDouble();
            /*
             * Ask user for k
             */
            out.print("Please enter value of order: ");
            double k = in.nextDouble();
            /*
             * Call method kthrt(double x) to compute k-th root and print
             */
            out.println("The root of input value is "
                    + kthrt(inputDouble, e, k));
            /*
             * Ask for a new value of x
             */
            out.println("Please enter a new non-negative number: ");
            inputDouble = in.nextDouble();

        } while (inputDouble >= 0); //quit loop if entering negative value
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }
}
