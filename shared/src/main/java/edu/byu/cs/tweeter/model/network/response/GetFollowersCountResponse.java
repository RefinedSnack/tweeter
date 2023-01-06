package edu.byu.cs.tweeter.model.network.response;

public class GetFollowersCountResponse extends GetCountResponse
{
    public GetFollowersCountResponse(String message)
    {
        super(message);
    }

    public GetFollowersCountResponse(int count)
    {
        super(count);
    }
}
