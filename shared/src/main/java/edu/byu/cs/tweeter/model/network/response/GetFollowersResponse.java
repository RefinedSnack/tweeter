package edu.byu.cs.tweeter.model.network.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersResponse extends PagedResponse<User>
{
    public GetFollowersResponse(String message)
    {
        super(message);
    }

    public GetFollowersResponse(boolean hasMorePages, List<User> users)
    {
        super(hasMorePages, users);
    }
}
