package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetUserRequest extends TargetedUserRequest
{

    protected GetUserRequest()
    {
    }

    public GetUserRequest(AuthToken authToken, String targetAlias)
    {
        super(authToken, targetAlias);
    }

}
