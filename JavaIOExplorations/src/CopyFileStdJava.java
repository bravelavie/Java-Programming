import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to copy a text file into another file.
 *
 * @author Put your name here
 *
 */
public final class CopyFileStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) {

        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * read input file.
         */
        out.print("Enter the name for the input file: ");
        String inputFileName = in.nextLine();
        SimpleReader inputFile = new SimpleReader1L(inputFileName);
        /*
         * create output file
         */
        out.print("Enter the name for the output file: ");
        String outputFileName = in.nextLine();
        SimpleWriter outputFile = new SimpleWriter1L(outputFileName);
        /*
         * copy input to output
         */
        while (!inputFile.atEOS()) {
            String line = inputFile.nextLine();
            outputFile.println(line);
        }

        inputFile.close();
        outputFile.close();
        in.close();
        out.close();
    }

}
