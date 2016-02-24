import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * reads multiple RSS feeds and generates the same nicely formatted HTML page of
 * links for each feed, plus an HTML index page with links to the individual
 * feed pages.
 *
 * @author Kun Liu
 *
 */
public final class RSSAggregator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSAggregator() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>the channel tag title as the page title</title>
     * </head> <body> <h1>the page title inside a link to the <channel> link<h1>
     * <p>
     * the channel description
     * </p>
     * <table>
     * <tr>
     * <th>Date</th>
     * <th>Source</th>
     * <th>News</th>
     * </tr>
     *
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        XMLTree title = channel.child(0);
        XMLTree link = channel.child(0);
        XMLTree description = channel.child(0);

        out.println("<html>");
        out.println("<head>");

        for (int i = 0; i < channel.numberOfChildren(); i++) {
            if (channel.child(i).label().equals("title")) {
                title = channel.child(i); //find child "title"
            }
            if (channel.child(i).label().equals("link")) {
                link = channel.child(i); //find child "link"
            }
            if (channel.child(i).label().equals("description")) {
                description = channel.child(i); //find child "description"
            }
        }

        /*
         * print out title
         */
        if (title.numberOfChildren() == 0) {
            out.println("<title> Empty Title </title>");
        } else {
            out.println("<title> " + title.child(0) + " </title>");
        }
        out.println("</head>");
        out.println("<body>");

        /*
         * add link to the page title
         */
        out.println("<h1><a href=\"" + link.child(0) + "\">" + title.child(0)
                + "</a></h1>");

        /*
         * print out description
         */
        if (description.numberOfChildren() == 0) {
            out.println("<p> No description available</p>");
        } else {
            out.println("<p>" + description.child(0) + "</p>");
        }

        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>Date</th>");
        out.println("<th>Source</th>");
        out.println("<th>News</th>");
        out.println("</tr>");
    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </table> </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of XML is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        int index = -1;

        for (int i = 0; i < xml.numberOfChildren(); i++) {
            if (xml.child(i).isTag()) {
                if (xml.child(i).label().equals(tag)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires <pre>
     * [the label of the root of item is an <item> tag] and out.is_open
     * </pre>
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        String pubDate = "No date available";
        String source = "No source available";
        String sourceLink = "";
        String title = "No title available";
        String newsLink = "";

        out.println("<tr>");
        /*
         * find and print out publication date
         */
        if (getChildElement(item, "pubDate") != -1) {
            pubDate = item.child(getChildElement(item, "pubDate")).child(0)
                    .label();
        }
        out.println("<td>" + pubDate + "</td>");

        /*
         * find and print out source
         */
        if (getChildElement(item, "source") != -1) {
            source = item.child(getChildElement(item, "source")).child(0)
                    .label();
            sourceLink = item.child(getChildElement(item, "source"))
                    .attributeValue("url");
            out.println("<td><a href=\"" + sourceLink + "\">" + source
                    + "</a></td>");
        } else {
            out.println("<td>" + source + "</td>");
        }

        /*
         * find and print out title
         */
        if (getChildElement(item, "title") != -1
                && item.child(getChildElement(item, "title"))
                        .numberOfChildren() != 0) { //child "title" exists and not empty
            title = item.child(getChildElement(item, "title")).child(0).label();
        } else if (getChildElement(item, "description") != -1
                && item.child(getChildElement(item, "description"))
                        .numberOfChildren() != 0) { //child "description" exists not empty
            title = item.child(getChildElement(item, "description")).child(0)
                    .label();
        }

        /*
         * find and print out link to the news if available
         */
        if (getChildElement(item, "link") != -1) {
            newsLink = item.child(getChildElement(item, "link")).child(0)
                    .label();
            out.println("<td><a href=\"" + newsLink + "\">" + title
                    + "</a></td>");
        } else {
            out.println("<td>" + title + "</td>");
        }

        out.println("</tr>");
    }

    /**
     * Processes one XML RSS (version 2.0) feed from a given URL converting it
     * into the corresponding HTML output file.
     *
     * @param url
     *            the URL of the RSS feed
     * @param file
     *            the name of the HTML output file
     * @param out
     *            the output stream to report progress or errors
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * [reads RSS feed from URL, saves HTML document with table of news items
     *   to file, appends to out.content any needed messages]
     * </pre>
     */
    private static void processFeed(String url, String file, SimpleWriter out) {

        /*
         * create a XML tree with one of the RSS URLs
         */
        XMLTree rss = new XMLTree1(url);
        /*
         * create a HTML output file
         */
        SimpleWriter fileOut = new SimpleWriter1L(file);

        /*
         * check if the link connects to a valid RSS 2.0 feed
         */
        if (!(rss.label().equals("rss")) || !(rss.hasAttribute("version"))
                || !(rss.attributeValue("version").equals("2.0"))) {
            fileOut.print(file + " is not a a valid RSS 2.0 feed.");
        } else {
            /*
             * create a HTML output file with title and table
             */
            XMLTree channel = rss.child(0);
            outputHeader(channel, fileOut);

            for (int i = 0; i < channel.numberOfChildren(); i++) {
                if (channel.child(i).isTag()) {
                    if (channel.child(i).label().equals("item")) {
                        processItem(channel.child(i), fileOut);
                    }
                }
            }

            outputFooter(fileOut);

            /*
             * construct one element of the list in the index HTML page
             */
            out.println("<li>");

            XMLTree title = channel.child(getChildElement(channel, "title"));

            /*
             * link the created HTML page to the index page
             */
            if (title.numberOfChildren() == 0) {
                out.println("<a href=\"" + file + "\">Empty Title</a>");
            } else {
                out.println("<a href=\"" + file + "\">" + title.child(0)
                        + "</a>");
            }
            out.println("</li>");

            fileOut.close();
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     *
     * @requires a valid XML file input
     *
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println("Please enter the name of input XML file: ");

        XMLTree rssFeeds = new XMLTree1(in.nextLine() + ".xml");

        out.println("Please enter the name of an output html file: ");
        SimpleWriter fileOut = new SimpleWriter1L(in.nextLine() + ".html");

        /*
         * construct the index page
         */
        fileOut.println("<html>");

        fileOut.println("<head>");
        fileOut.println("<title>" + rssFeeds.attributeValue("title")
                + "</title>");
        fileOut.println("</head>");
        fileOut.println("<body>");

        fileOut.println("<h2>" + rssFeeds.attributeValue("title") + "</h2>");
        fileOut.println("<ul>");

        /*
         * call processFeed to print each item of the list and corresponding
         * page
         */
        for (int i = 0; i < rssFeeds.numberOfChildren(); i++) {
            String url = rssFeeds.child(i).attributeValue("url");
            String file = rssFeeds.child(i).attributeValue("file");
            processFeed(url, file, fileOut);
        }

        fileOut.println("</ul>");
        fileOut.println("</body>");
        fileOut.println("</html>");

        in.close();
        out.close();
        fileOut.close();
    }

}