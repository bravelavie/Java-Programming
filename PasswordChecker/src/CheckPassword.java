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
public final class CheckPassword {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CheckPassword() {
    }

    /**
     * Checks whether the given String satisfies the CSE department criteria for
     * a valid password. Prints an appropriate message to the given output
     * stream.
     *
     * @param s
     *            the String to check
     * @param out
     *            the output stream
     */
    private static void checkPassword(String s, SimpleWriter out) {
        final int LENGTH = 6;
        if (s.length() < LENGTH) {
            out.println("Passwords must be at least 6 characters long");
        }
        boolean a = containsUpperCaseLetter(s), b = containsLowerCaseLetter(s), c = containsDigit(s), d = containsSpecialChar(s);
        if (!((a && b && c) || (b && c && d) || (a && c && d) || (a && b && d))) {
            out.println("Passwords must contain characters from at least three of the four types:");
            out.println("upper case letters, lower case letters, digits, and sepcial charaters:!@#$%^&*()_-+={}[]:;,.?");
        } else {
            out.println("Pass!");
        }
    }

    /**
     * Checks if the given String contains an upper case letter.
     *
     * @param s
     *            the String to check
     * @return true if s contains an upper case letter, false otherwise
     */
    private static boolean containsUpperCaseLetter(String s) {
        boolean result = false;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if the given String contains an lower case letter.
     *
     * @param s
     *            the String to check
     * @return true if s contains an lower case letter, false otherwise
     */
    private static boolean containsLowerCaseLetter(String s) {
        boolean result = false;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if the given String contains an digit.
     *
     * @param s
     *            the String to check
     * @return true if s contains an digit, false otherwise
     */
    private static boolean containsDigit(String s) {
        boolean result = false;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if the given String contains a special character.
     *
     * @param s
     *            the String to check
     * @return true if s contains a special character, false otherwise
     */
    private static boolean containsSpecialChar(String s) {
        String specialChar = "!@#$%^&*()_-+={}[]:;,.?";
        boolean result = false;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < specialChar.length(); j++) {
                if (s.charAt(i) == specialChar.charAt(j)) {
                    result = true;
                }

            }
        }
        return result;
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
        out.print("Please enter your password: ");
        String passWord = in.nextLine();
        do {
            checkPassword(passWord, out);
            out.print("Please enter your password: ");
            passWord = in.nextLine();
        } while (!(passWord.isEmpty()));

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
