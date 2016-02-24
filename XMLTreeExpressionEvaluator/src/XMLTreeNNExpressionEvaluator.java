import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code NaturalNumber}.
 *
 * @author Kun Liu
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        /*
         * Check precondition:the label of the root of exp is not "expression"
         */
        Reporter.assertElseFatalError(!(exp.label() == "expression"),
                "Error:the root label of exp cannot be\"expression\"");

        NaturalNumber result = new NaturalNumber2();
        if (exp.label().equals("number")) {
            result.transferFrom(new NaturalNumber2(exp.attributeValue("value")));
        } else if (exp.label().equals("plus")) {
            result.transferFrom(evaluate(exp.child(0)));
            result.add(evaluate(exp.child(1)));
        } else if (exp.label().equals("minus")) {
            /*
             * Check if the result after minus operation is negative
             */
            if (evaluate(exp.child(0)).compareTo(evaluate(exp.child(1))) < 0) {
                Reporter.fatalErrorToConsole("Error: NaturalNumber cannot be negative");
            } else {
                result.transferFrom(evaluate(exp.child(0)));
                result.subtract(evaluate(exp.child(1)));
            }
        } else if (exp.label().equals("times")) {
            result.transferFrom(evaluate(exp.child(0)));
            result.multiply(evaluate(exp.child(1)));
        } else if (exp.label().equals("divide")) {
            result.transferFrom(evaluate(exp.child(0)));
            /*
             * Check if the dividend is zero
             */
            if (evaluate(exp.child(1)).isZero()) {
                Reporter.fatalErrorToConsole("Error: cannot be divided by 0");
            } else {
                result.divide(evaluate(exp.child(1)));
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}