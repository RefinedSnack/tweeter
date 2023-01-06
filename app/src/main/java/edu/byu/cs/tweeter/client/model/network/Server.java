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
import edu.byu.cs.tweeter.model.network.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.model.network.response.FollowUserResponse;
import edu.byu.cs.tweeter.model.network.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowersCountResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowingCountResponse;
import edu.byu.cs.tweeter.model.network.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.network.response.GetUserResponse;
import edu.byu.cs.tweeter.model.network.response.IsFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.LoginResponse;
import edu.byu.cs.tweeter.model.network.response.LogoutResponse;
import edu.byu.cs.tweeter.model.network.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.network.response.RegisterResponse;
import edu.byu.cs.tweeter.model.network.response.UnfollowUserResponse;

public interface Server
{
    RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException;

    LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;

    LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException;

    UnfollowUserResponse unfollowUser(UnfollowUserRequest request) throws IOException, TweeterRemoteException;
    FollowUserResponse followUser(FollowUserRequest request) throws IOException, TweeterRemoteException;

    IsFollowingResponse isFollowing(IsFollowingRequest request) throws IOException, TweeterRemoteException;

    PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException;

    GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException;

    GetFollowersCountResponse getNumFollowers(GetFollowersCountRequest request) throws IOException, TweeterRemoteException;

    GetFollowingCountResponse getFollowingCount(GetFollowingCountRequest request) throws IOException, TweeterRemoteException;

    GetStoryResponse getStory(GetStoryRequest request) throws IOException, TweeterRemoteException;

    GetFollowersResponse getFollowers(GetFollowersRequest request) throws IOException, TweeterRemoteException;

    GetFollowingResponse getFollowing(GetFollowingRequest request) throws IOException, TweeterRemoteException;

    GetFeedResponse getFeed(GetFeedRequest request) throws IOException, TweeterRemoteException;
}
