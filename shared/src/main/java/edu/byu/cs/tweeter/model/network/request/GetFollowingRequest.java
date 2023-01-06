package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class GetFollowingRequest extends PagedRequest<String>
{
    protected GetFollowingRequest()
    {
    }

    public GetFollowingRequest(AuthToken authToken, String targetAlias, int limit, String lastAlias)
    {
        super(authToken, targetAlias, limit, lastAlias);
    }
}