import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of {@code EmailAccount}.
 *
 * @author Kun Liu
 *
 */
public final class EmailAccount1 implements EmailAccount {

    /*
     * Private members --------------------------------------------------------
     */

    private String firstName;

    private String lastName;

    private int number;

    private static Map<String, Integer> account = new TreeMap<String, Integer>();

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Constructor.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public EmailAccount1(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.number = 1;
        String key = lastName.toLowerCase();
        if (account.containsKey(key)) {
            this.number = account.get(key) + 1;
            account.replace(key, this.number);
        } else {
            account.put(key, 1);
        }
    }

    /*
     * Methods ----------------------------------------------------------------
     */

    @Override
    public String name() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String emailAddress() {
        return this.lastName + "." + this.number;
    }

    @Override
    public String toString() {
        return "Name: " + this.name() + ", Email: " + this.emailAddress();
    }

}
