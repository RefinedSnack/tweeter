package edu.byu.cs.tweeter.model.network.response;

public class GetFollowingCountResponse extends GetCountResponse
{
    public GetFollowingCountResponse(String message)
    {
        super(message);
    }

    public GetFollowingCountResponse(int count)
    {
        super(count);
    }
}
