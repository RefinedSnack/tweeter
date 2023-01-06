package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetFollowingCountRequest extends GetCountRequest
{
    protected GetFollowingCountRequest()
    {
    }

    public GetFollowingCountRequest(AuthToken authToken, String targetAlias)
    {
        super(authToken, targetAlias);
    }
}

