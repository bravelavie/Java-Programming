import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * estimate any positive real-value with de Jager formula.
 *
 * @author Kun Liu
 *
 */
public final class ABCDGuesser2 {
    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser2() {
    }

    /**
     * Iteration to find the closest estimate of miu.
     *
     * @param miu
     *            the value to estimate
     * @param w
     *            personal number greater than 0 and not equal to 1
     * @param x
     *            personal number greater than 0 and not equal to 1
     * @param y
     *            personal number greater than 0 and not equal to 1
     * @param z
     *            personal number greater than 0 and not equal to 1
     */
    private static void iteration(double miu, double w, double x, double y,
            double z) {
        SimpleWriter out = new SimpleWriter1L();
        double[] index = { -5, -4, -3, -2, -1, -(1.0 / 2), -(1.0 / 3),
                -(1.0 / 4), 0, (1.0 / 4), (1.0 / 3), (1.0 / 2), 1, 2, 3, 4, 5 };
        double a, b, c, d, selectedA = 1, selectedB = 1, selectedC = 1, selectedD = 1;
        double bestEstimate = 0, newEstimate;
        /*
         * build nested loops to test all combinations of indices for the
         * formula
         */
        for (int i = 0; i < index.length; i++) {
            a = index[i];
            for (int j = 0; j < index.length; j++) {
                b = index[j];
                for (int k = 0; k < index.length; k++) {
                    c = index[k];
                    for (int l = 0; l < index.length; l++) {
                        d = index[l];
                        newEstimate = Math.pow(w, a) * Math.pow(x, b)
                                * Math.pow(y, c) * Math.pow(z, d);
                        /*
                         * keep replacing best estimate as it makes smallest
                         * error
                         */
                        if (Math.abs(miu - newEstimate) < Math.abs(miu
                                - bestEstimate)) {
                            bestEstimate = newEstimate;
                            selectedA = a;
                            selectedB = b;
                            selectedC = c;
                            selectedD = d;
                        }
                    }
                }
            }
        }
        /*
         * calculate relative error of final estimate
         */
        double error = Math.abs(miu - bestEstimate) / miu;

        out.println("the values of the exponents are: " + selectedA + ", "
                + selectedB + ", " + selectedC + ", " + selectedD);
        out.println("The value of the formula is: " + bestEstimate);
        out.println("realtive error of the approximation is: " + error * 100
                + "%");
        /*
         * Close output streams
         */
        out.close();
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
        out.print("Please enter a positive constant to estimate: ");
        double miu = in.nextDouble();
        out.println("Please enter any four positive numbers not equal to 1: ");
        double w = in.nextDouble();
        double x = in.nextDouble();
        double y = in.nextDouble();
        double z = in.nextDouble();
        /*
         * Call iteration method to find the closest approximation
         */
        iteration(miu, w, x, y, z);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
