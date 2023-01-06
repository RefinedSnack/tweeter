package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class AuthenticatedRequest extends Request
{

    private AuthToken authToken;

    protected AuthenticatedRequest()
    {
    }

    public AuthenticatedRequest(AuthToken authToken)
    {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken)
    {
        this.authToken = authToken;
    }
}
