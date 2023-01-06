package edu.byu.cs.tweeter.model.network.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryResponse extends PagedResponse<Status>
{
    protected GetStoryResponse(String message)
    {
        super(message);
    }

    public GetStoryResponse(boolean hasMorePages, List<Status> statuses)
    {
        super(hasMorePages, statuses);
    }
}
