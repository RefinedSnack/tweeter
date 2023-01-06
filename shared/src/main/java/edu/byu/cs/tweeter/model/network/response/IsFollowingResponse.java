package edu.byu.cs.tweeter.model.network.response;

public class IsFollowingResponse extends Response
{
    boolean isFollowing;

    public IsFollowingResponse(String message)
    {
        super(message);
    }

    public IsFollowingResponse(boolean isFollowing)
    {
        this.isFollowing = isFollowing;
    }

    public boolean isFollowing()
    {
        return isFollowing;
    }
}
