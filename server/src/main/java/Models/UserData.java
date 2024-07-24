package Models;

/**
 * UserData object stores username, password, and email.
 * It contains getters and setters for each.
 */
public class UserData {

    private String username;
    private String password;
    private String email;

    /**
     * Initialization of the object; the variables are self-explanatory.
     * @param name
     * @param password
     * @param email
     */
    public UserData(String name, String password, String email) {
        username = name;
        this.password = password;
        this.email = email;

    }

    // Get username
    public String getUsername() {
        return username;
    }

    // Set username
    public void setUsername(String username) {
        this.username = username;
    }

    // Get password
    public String getPassword() {
        return password;
    }

    // Set password
    public void setPassword(String password) {
        this.password = password;
    }

    // Get email
    public String getEmail() {
        return email;
    }

    // Set email
    public void setEmail(String email) {
        this.email = email;
    }
}
