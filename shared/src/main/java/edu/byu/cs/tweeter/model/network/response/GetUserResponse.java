package edu.byu.cs.tweeter.model.network.response;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse extends Response
{
    private User user;

    public GetUserResponse(String message)
    {
        super(message);
    }

    public GetUserResponse(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}
