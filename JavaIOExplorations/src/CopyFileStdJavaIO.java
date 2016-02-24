import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Program to copy a text file into another file.
 *
 * @author Put your name here
 *
 */
public final class CopyFileStdJavaIO {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJavaIO() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) throws IOException {
        /*
         * read input file.
         */
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name for the input file: ");
        String inputFileName = input.nextLine();
        Scanner inputFile = new Scanner(inputFileName);
        /*
         * create output file.
         */
        System.out.print("Enter the name for the output file: ");
        String outputFileName = input.nextLine();
        PrintWriter outputFile = new PrintWriter(new BufferedWriter(
                new FileWriter(outputFileName)));
        /*
         * copy input to output
         */
        while (inputFile != null) {
            String line = inputFile.nextLine();
            outputFile.println(line);
        }

        inputFile.close();
        input.close();
        outputFile.close();

    }
}
