package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowUserRequest extends TargetedUserRequest
{
    protected FollowUserRequest()
    {
    }

    public FollowUserRequest(AuthToken authToken, String targetAlias)
    {
        super(authToken, targetAlias);
    }
}
