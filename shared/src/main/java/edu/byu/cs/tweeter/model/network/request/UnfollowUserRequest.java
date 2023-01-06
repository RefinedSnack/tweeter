package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UnfollowUserRequest extends TargetedUserRequest
{
    protected UnfollowUserRequest()
    {
    }

    public UnfollowUserRequest(AuthToken authToken, String targetAlias)
    {
        super(authToken, targetAlias);
    }
}