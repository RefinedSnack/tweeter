package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogoutRequest extends AuthenticatedRequest
{
    protected LogoutRequest()
    {
    }

    public LogoutRequest(AuthToken authToken)
    {
        super(authToken);
    }
}
