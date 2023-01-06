package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class GetCountRequest extends TargetedUserRequest
{
    protected GetCountRequest()
    {
    }

    public GetCountRequest(AuthToken authToken, String targetAlias)
    {
        super(authToken, targetAlias);
    }
}
