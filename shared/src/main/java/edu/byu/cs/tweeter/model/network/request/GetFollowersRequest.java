package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetFollowersRequest extends PagedRequest<String>
{
    protected GetFollowersRequest()
    {
    }

    public GetFollowersRequest(AuthToken authToken, String targetAlias, int limit, String lastAlias)
    {
        super(authToken, targetAlias, limit, lastAlias);
    }
}