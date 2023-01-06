package edu.byu.cs.tweeter.model.network.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingResponse extends PagedResponse<User>
{
    public GetFollowingResponse(String message)
    {
        super(message);
    }

    public GetFollowingResponse(boolean hasMorePages, List<User> users)
    {
        super(hasMorePages, users);
    }
}
