package Models;

import java.util.Objects;

/**
 * AuthData object stores username and authToken.
 * It contains getters and setters for each.
 */
public class AuthData {

    private String username;
    private String authToken;

    /**
     * Initialization of the object
     * @param username is the user
     * @param authToken is the authorization token assigned to the user
     */
    public AuthData(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    /**
     * Getters and Setters
     */
    // Get username
    public String getUsername() {
        return username;
    }

    // Set username
    public void setUsername(String username) {
        this.username = username;
    }

    // Get authorization token
    public String getAuthToken() {
        return authToken;
    }

    // Set authorization token
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Equals and hashCode functions
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthData authData)) return false;
        return Objects.equals(username, authData.username) && Objects.equals(authToken, authData.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authToken);
    }
}
