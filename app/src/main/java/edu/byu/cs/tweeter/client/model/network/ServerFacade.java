package edu.byu.cs.tweeter.client.model.network;

import java.io.IOException;

import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.FollowUserRequest;
import edu.byu.cs.tweeter.model.network.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.network.request.GetFollowersCountRequest;
import edu.byu.cs.tweeter.model.network.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.network.request.GetFollowingCountRequest;
import edu.byu.cs.tweeter.model.network.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.network.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.network.request.GetUserRequest;
import edu.byu.cs.tweeter.model.network.request.IsFollowingRequest;
import edu.byu.cs.tweeter.model.network.request.LoginRequest;
import edu.byu.cs.tweeter.model.network.request.LogoutRequest;
import edu.byu.cs.tweeter.model.network.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.network.request.RegisterRequest;
import edu.byu.cs.tweeter.model.network.request.Request;
import edu.byu.cs.tweeter.model.network.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.model.network.response.Response;

public class ServerFacade
{
    private Server server;

    public Server getServer()
    {
        if (server == null)
            server = serverFactory();
        return server;
    }

    public Server serverFactory()
    {
        return new DummyDataServer();
    }

    public URLPathManager URLPathManagerFactory()
    {
        return new ConstantURLPathManager();
    }


    public Response run(Request request, Class<?> type) throws IOException, TweeterRemoteException
    {
        if (type == RegisterRequest.class)
            return getServer().register((RegisterRequest) request);
        if (type == LoginRequest.class)
            return getServer().login((LoginRequest) request);
        if (type == LogoutRequest.class)
            return getServer().logout((LogoutRequest) request);
        if (type == UnfollowUserRequest.class)
            return getServer().unfollowUser((UnfollowUserRequest) request);
        if (type == FollowUserRequest.class)
            return getServer().followUser((FollowUserRequest) request);
        if (type == IsFollowingRequest.class)
            return getServer().isFollowing((IsFollowingRequest) request);
        if (type == PostStatusRequest.class)
            return getServer().postStatus((PostStatusRequest) request);
        if (type == GetUserRequest.class)
            return getServer().getUser((GetUserRequest) request);
        if (type == GetFollowersCountRequest.class)
            return getServer().getNumFollowers((GetFollowersCountRequest) request);
        if (type == GetFollowingCountRequest.class)
            return getServer().getNumFollowing((GetFollowingCountRequest) request);
        if (type == GetStoryRequest.class)
            return getServer().getStory((GetStoryRequest) request);
        if (type == GetFollowersRequest.class)
            return getServer().getFollowers((GetFollowersRequest) request);
        if (type == GetFollowingRequest.class)
            return getServer().getFollowing((GetFollowingRequest) request);
        if (type == GetFeedRequest.class)
            return getServer().getFeed((GetFeedRequest) request);

        return null;
    }
}
