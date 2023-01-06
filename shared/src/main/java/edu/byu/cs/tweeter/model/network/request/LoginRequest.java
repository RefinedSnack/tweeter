package edu.byu.cs.tweeter.model.network.request;

/**
 * Contains all the information needed to make a login request.
 */
public class LoginRequest extends Request
{

    private String username;
    private String password;

    protected LoginRequest()
    {
        super();
    }

    public LoginRequest(String username, String password)
    {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username.
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Returns the password of the user to be logged in by this request.
     *
     * @return the password.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}
