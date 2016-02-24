import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Program to copy a text file into another file.
 *
 * @author Put your name here
 *
 */
public final class FileStdJavaIOException {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private FileStdJavaIOException() {
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
        BufferedReader input = new BufferedReader(new InputStreamReader(
                System.in));
        System.out.print("Enter the name for the input file: ");
        String inputFileName = input.readLine();
        BufferedReader inputFile = new BufferedReader(new FileReader(
                inputFileName));
        /*
         * create output file.
         */
        System.out.print("Enter the name for the output file: ");
        String outputFileName = input.readLine();
        PrintWriter outputFile = new PrintWriter(new BufferedWriter(
                new FileWriter(outputFileName)));
        /*
         * copy input to output
         */
        String line = inputFile.readLine();
        while (line != null) {
            line = inputFile.readLine();
            outputFile.println(line);
        }

        inputFile.close();
        input.close();
        outputFile.close();
    }
}
