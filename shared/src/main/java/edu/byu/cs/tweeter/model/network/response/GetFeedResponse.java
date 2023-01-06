package edu.byu.cs.tweeter.model.network.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedResponse extends PagedResponse<Status>
{
    public GetFeedResponse(String message)
    {
        super(message);
    }

    public GetFeedResponse(boolean hasMorePages, List<Status> statuses)
    {
        super(hasMorePages, statuses);
    }
}
