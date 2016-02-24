import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class XMLTreeExploration {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeExploration() {
    }

    /**
     * Output all attributes names and values for the root of the given
     * {@code XMLTree}.
     *
     * @param xt
     *            the {@code XMLTree} whose root's attributes are to be printed
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of xt is a tag] and out.is_open
     * @ensures <pre>
     * out.content =
     *   #out.content *  [the name and value of each attribute of the root of xt]
     * </pre>
     */
    private static void printRootAttributes(XMLTree xt, SimpleWriter out) {

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
        /*
         * Put your main program code here
         */
        XMLTree xml = new XMLTree1(
                "http://xml.weather.yahoo.com/forecastrss/43210.xml");
        //out.print(xml.toString());
        xml.display();
        //out.println(xml.isTag());
        //out.print(xml.label());
        XMLTree channel = xml.child(0);
        //channel.display();
        out.print(channel.numberOfChildren());

        XMLTree title = channel.child(0);
        //title.display();
        XMLTree titleText = title.child(0);
        ;
        out.println(titleText.label());

        out.println(xml.child(0).child(0).child(0).label());

        XMLTree astronomy = xml.child(0).child(10);
        out.println(astronomy.hasAttribute("sunset"));
        out.println(astronomy.hasAttribute("midday"));
        out.println(astronomy.attributeValue("sunset"));
        out.println(astronomy.attributeValue("sunrise"));
        out.println(xml.hasAttribute("sunset"));
        out.println(channel.child(10).label());
        /*
         * 
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
