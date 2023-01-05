package edu.byu.cs.tweeter.model.network;

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
import edu.byu.cs.tweeter.model.network.response.UnfollowUserResponse;
import edu.byu.cs.tweeter.model.network.response.UploadStatusResponse;

public class Explore
{
    @Register
    {
        RegisterRequest request = new RegisterRequest();
        RegisterResponse response = (RegisterResponse) getResponse(request, RegisterRequest.class);
        return response;
    }
    @Login
    {
        LoginRequest request = new LoginRequest();
        LoginResponse response = (LoginResponse) getResponse(request, LoginRequest.class);
        return response;
    }
    @Logout
    {
        LogoutRequest request = new LogoutRequest();
        LogoutResponse response = (LogoutResponse) getResponse(request, LogoutRequest.class);
        return response;
    }
    @FollowUser
    {
        FollowUserRequest request = new FollowUserRequest();
        FollowUserResponse response = (FollowUserResponse) getResponse(request, FollowUserRequest.class);
        return response;
    }
    @UnfollowUser
    {
        UnfollowUserRequest request = new UnfollowUserRequest();
        UnfollowUserResponse response = (UnfollowUserResponse) getResponse(request, UnfollowUserRequest.class);
        return response;
    }
    @IsFollowing
    {
        IsFollowingRequest request = new IsFollowingRequest();
        IsFollowingResponse response = (IsFollowingResponse) getResponse(request, IsFollowingRequest.class);
        return response;
    }
    @UploadStatus
    {
        UploadStatusRequest request = new UploadStatusRequest();
        UploadStatusResponse response = (UploadStatusResponse) getResponse(request, UploadStatusRequest.class);
        return response;
    }
    @GetUser
    {
        GetUserRequest request = new GetUserRequest();
        GetUserResponse response = (GetUserResponse) getResponse(request, GetUserRequest.class);
        return response;
    }
    @GetNumFollowers
    {
        GetNumFollowersRequest request = new GetNumFollowersRequest();
        GetNumFollowersResponse response = (GetNumFollowersResponse) getResponse(request, GetNumFollowersRequest.class);
        return response;
    }
    @GetNumFollowing
    {
        GetNumFollowingRequest request = new GetNumFollowingRequest();
        GetNumFollowingResponse response = (GetNumFollowingResponse) getResponse(request, GetNumFollowingRequest.class);
        return response;
    }
    @GetStory
    {
        GetStoryRequest request = new GetStoryRequest();
        GetStoryResponse response = (GetStoryResponse) getResponse(request, GetStoryRequest.class);
        return response;
    }
    @GetFollowers
    {
        GetFollowersRequest request = new GetFollowersRequest();
        GetFollowersResponse response = (GetFollowersResponse) getResponse(request, GetFollowersRequest.class);
        return response;
    }
    @GetFollowing
    {
        GetFollowingRequest request = new GetFollowingRequest();
        GetFollowingResponse response = (GetFollowingResponse) getResponse(request, GetFollowingRequest.class);
        return response;
    }
    @GetFeed
    {
        GetFeedRequest request = new GetFeedRequest();
        GetFeedResponse response = (GetFeedResponse) getResponse(request, GetFeedRequest.class);
        return response;
    }
}
