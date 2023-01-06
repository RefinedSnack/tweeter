package edu.byu.cs.tweeter.model.network.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticateResponse extends Response
{

    private User user;
    private AuthToken authToken;

    protected AuthenticateResponse(String message)
    {
        super(message);
    }

    protected AuthenticateResponse(User user, AuthToken authToken)
    {
        super();
        this.user = user;
        this.authToken = authToken;
    }

    public User getUser()
    {
        return user;
    }

    public AuthToken getAuthToken()
    {
        return authToken;
    }


}
