package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetFollowersCountRequest extends GetCountRequest
{
    protected GetFollowersCountRequest()
    {
    }

    public GetFollowersCountRequest(AuthToken authToken, String targetAlias)
    {
        super(authToken, targetAlias);
    }
}
