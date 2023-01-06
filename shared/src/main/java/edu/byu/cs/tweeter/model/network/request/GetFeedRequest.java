package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedRequest extends PagedRequest<Status>
{
    protected GetFeedRequest()
    {

    }

    public GetFeedRequest(AuthToken authToken, String targetAlias, int limit, Status last)
    {
        super(authToken, targetAlias, limit, last);
    }
}
