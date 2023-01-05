package edu.byu.cs.tweeter.client.model.network;

import java.io.IOException;

import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.FollowUserRequest;
import edu.byu.cs.tweeter.model.network.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.network.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.network.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.network.request.GetNumFollowersRequest;
import edu.byu.cs.tweeter.model.network.request.GetNumFollowingRequest;
import edu.byu.cs.tweeter.model.network.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.network.request.GetUserRequest;
import edu.byu.cs.tweeter.model.network.request.IsFollowingRequest;
import edu.byu.cs.tweeter.model.network.request.LoginRequest;
import edu.byu.cs.tweeter.model.network.request.LogoutRequest;
import edu.byu.cs.tweeter.model.network.request.RegisterRequest;
import edu.byu.cs.tweeter.model.network.request.Request;
import edu.byu.cs.tweeter.model.network.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.model.network.request.UploadStatusRequest;
import edu.byu.cs.tweeter.model.network.response.FollowUserResponse;
import edu.byu.cs.tweeter.model.network.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.GetNumFollowersResponse;
import edu.byu.cs.tweeter.model.network.response.GetNumFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.network.response.GetUserResponse;
import edu.byu.cs.tweeter.model.network.response.IsFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.LoginResponse;
import edu.byu.cs.tweeter.model.network.response.LogoutResponse;
import edu.byu.cs.tweeter.model.network.response.RegisterResponse;
import edu.byu.cs.tweeter.model.network.response.Response;
import edu.byu.cs.tweeter.model.network.response.UnfollowUserResponse;
import edu.byu.cs.tweeter.model.network.response.UploadStatusResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade
{
    private URLPathManager pathManager;

    public Response run(Request request, Class<?> type) throws IOException, TweeterRemoteException
    {
        if (type == RegisterRequest.class)
            return register((RegisterRequest) request);
        if (type == LoginRequest.class)
            return login((LoginRequest) request);
        if (type == LogoutRequest.class)
            return logout((LogoutRequest) request);
        if (type == UnfollowUserRequest.class)
            return unfollowUser((UnfollowUserRequest) request);
        if (type == FollowUserRequest.class)
            return followUser((FollowUserRequest) request);
        if (type == IsFollowingRequest.class)
            return isFollowing((IsFollowingRequest) request);
        if (type == UploadStatusRequest.class)
            return uploadStatus((UploadStatusRequest) request);
        if (type == GetUserRequest.class)
            return getUser((GetUserRequest) request);
        if (type == GetNumFollowersRequest.class)
            return getNumFollowers((GetNumFollowersRequest) request);
        if (type == GetNumFollowingRequest.class)
            return getNumFollowing((GetNumFollowingRequest) request);
        if (type == GetStoryRequest.class)
            return getStory((GetStoryRequest) request);
        if (type == GetFollowersRequest.class)
            return getFollowers((GetFollowersRequest) request);
        if (type == GetFollowingRequest.class)
            return getFollowing((GetFollowingRequest) request);
        if (type == GetFeedRequest.class)
            return getFeed((GetFeedRequest) request);

        return null;
    }

    public ServerFacade(URLPathManager pathManager)
    {
        this.pathManager = pathManager;
    }

    private RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private UnfollowUserResponse unfollowUser(UnfollowUserRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }
    private FollowUserResponse followUser(FollowUserRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private IsFollowingResponse isFollowing(IsFollowingRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private UploadStatusResponse uploadStatus(UploadStatusRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private GetNumFollowersResponse getNumFollowers(GetNumFollowersRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private GetNumFollowingResponse getNumFollowing(GetNumFollowingRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private GetStoryResponse getStory(GetStoryRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private GetFollowersResponse getFollowers(GetFollowersRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private GetFollowingResponse getFollowing(GetFollowingRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }

    private GetFeedResponse getFeed(GetFeedRequest request) throws IOException, TweeterRemoteException
    {
        return null;
    }
}