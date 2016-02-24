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
public final class ABCDGuesser {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser() {
    }

    /**
     * Put a short phrase describing the static method myMethod here.
     */
    private static double deJager(double a, double b, double c, double d,
            double w, double x, double y, double z) {
        return Math.pow(w, a) * Math.pow(x, b) * Math.pow(y, c)
                * Math.pow(z, d);
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
        double[] index = { -5, -4, -3, -2, -1, -1 / 2, -1 / 3, -1 / 4, 0,
                1 / 4, 1 / 3, 1 / 2, 1, 2, 3, 4, 5 };
        int i = 0, j = 0, k = 0, l = 0;
        double a, b, c, d, selectedA = 1, selectedB = 1, selectedC = 1, selectedD = 1;
        double bestEstimate = 0, newEstimate;
        while (i < index.length) {
            a = index[i];
            while (j < index.length) {
                b = index[j];
                while (k < index.length) {
                    c = index[k];
                    while (l < index.length) {
                        d = index[l];
                        newEstimate = deJager(a, b, c, d, w, x, y, z);
                        if (Math.abs(miu - newEstimate) < Math.abs(miu
                                - bestEstimate)) {
                            bestEstimate = newEstimate;
                            selectedA = a;
                            selectedB = b;
                            selectedC = c;
                            selectedD = d;
                        }
                        l++;
                    }
                    k++;
                }
                j++;
            }
            i++;
        }
        double error = Math.abs(miu - bestEstimate) / miu * 100;
        out.println("the values of the exponents are: " + selectedA + ", "
                + selectedB + ", " + selectedC + ", " + selectedD);
        out.println("The value of the formula is: " + bestEstimate);
        out.println("realtive error of the approximation is: " + error + "%");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}