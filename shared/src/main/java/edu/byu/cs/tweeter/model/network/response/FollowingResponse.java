package edu.byu.cs.tweeter.model.network.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowingResponse extends PagedResponse<User>
{
    public FollowingResponse(String message)
    {
        super(message);
    }

    public FollowingResponse(boolean hasMorePages, List<User> users)
    {
        super(hasMorePages, users);
    }
}
