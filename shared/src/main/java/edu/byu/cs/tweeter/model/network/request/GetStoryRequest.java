package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryRequest extends PagedRequest<Status>
{
    protected GetStoryRequest()
    {
    }

    public GetStoryRequest(AuthToken authToken, String target, int limit, Status last)
    {
        super(authToken, target, limit, last);
    }
}