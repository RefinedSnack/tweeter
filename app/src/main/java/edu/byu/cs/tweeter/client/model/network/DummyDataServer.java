package edu.byu.cs.tweeter.client.model.network;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
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
import edu.byu.cs.tweeter.model.network.response.GetFollowersCountResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowingCountResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.network.response.GetUserResponse;
import edu.byu.cs.tweeter.model.network.response.IsFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.LoginResponse;
import edu.byu.cs.tweeter.model.network.response.LogoutResponse;
import edu.byu.cs.tweeter.model.network.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.network.response.RegisterResponse;
import edu.byu.cs.tweeter.model.network.response.UnfollowUserResponse;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class DummyDataServer implements Server
{
    private FakeData fakeData = FakeData.getInstance();
    @Override
    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException
    {
        User user = fakeData.getFirstUser();
        AuthToken authToken = fakeData.getAuthToken();
        return new RegisterResponse(user, authToken);
    }

    @Override
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException
    {
        User user = fakeData.getFirstUser();
        AuthToken authToken = fakeData.getAuthToken();
        return new LoginResponse(user, authToken);
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException
    {
        return new LogoutResponse();
    }

    @Override
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) throws IOException, TweeterRemoteException
    {
        return new UnfollowUserResponse();
    }

    @Override
    public FollowUserResponse followUser(FollowUserRequest request) throws IOException, TweeterRemoteException
    {
        return new FollowUserResponse();
    }

    @Override
    public IsFollowingResponse isFollowing(IsFollowingRequest request) throws IOException, TweeterRemoteException
    {
        return new IsFollowingResponse(new Random().nextInt() > 0);
    }

    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException
    {
        return new PostStatusResponse();
    }

    @Override
    public GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException
    {
        User user = fakeData.findUserByAlias(request.getTargetAlias());
        return new GetUserResponse(user);
    }

    @Override
    public GetFollowersCountResponse getNumFollowers(GetFollowersCountRequest request) throws IOException, TweeterRemoteException
    {
        return new GetFollowersCountResponse(20);
    }

    @Override
    public GetFollowingCountResponse getNumFollowing(GetFollowingCountRequest request) throws IOException, TweeterRemoteException
    {
        return new GetFollowingCountResponse(20);
    }

    @Override
    public GetStoryResponse getStory(GetStoryRequest request) throws IOException, TweeterRemoteException
    {
        Pair<List<Status>, Boolean> pageOfItems = fakeData.getPageOfStatus(request.getLast(), request.getLimit());
        List<Status> statuses = pageOfItems.getFirst();
        Boolean hasMorePages = pageOfItems.getSecond();

        return new GetStoryResponse(hasMorePages, statuses);
    }

    @Override
    public GetFollowersResponse getFollowers(GetFollowersRequest request) throws IOException, TweeterRemoteException
    {
        User lastUser = fakeData.findUserByAlias(request.getLast());
        Pair<List<User>, Boolean> pageOfItems = fakeData.getPageOfUsers(lastUser, request.getLimit(), fakeData.getFirstUser());
        List<User> users = pageOfItems.getFirst();
        Boolean hasMorePages = pageOfItems.getSecond();

        return new GetFollowersResponse(hasMorePages, users);
    }

    @Override
    public GetFollowingResponse getFollowing(GetFollowingRequest request) throws IOException, TweeterRemoteException
    {
        User lastUser = fakeData.findUserByAlias(request.getLast());
        Pair<List<User>, Boolean> pageOfItems = fakeData.getPageOfUsers(lastUser, request.getLimit(), fakeData.getFirstUser());
        List<User> users = pageOfItems.getFirst();
        Boolean hasMorePages = pageOfItems.getSecond();

        return new GetFollowingResponse(hasMorePages, users);
    }

    @Override
    public GetFeedResponse getFeed(GetFeedRequest request) throws IOException, TweeterRemoteException
    {
        Pair<List<Status>, Boolean> pageOfItems = fakeData.getPageOfStatus(request.getLast(), request.getLimit());
        List<Status> statuses = pageOfItems.getFirst();
        Boolean hasMorePages = pageOfItems.getSecond();

        return new GetFeedResponse(hasMorePages, statuses);
    }
}
