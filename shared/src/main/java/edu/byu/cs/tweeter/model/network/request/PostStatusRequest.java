package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusRequest extends AuthenticatedRequest
{
    Status status;

    protected PostStatusRequest()
    {
    }

    public PostStatusRequest(AuthToken authToken, Status status)
    {
        super(authToken);
        this.status = status;
    }
}
