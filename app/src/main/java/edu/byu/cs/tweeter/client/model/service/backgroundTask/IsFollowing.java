package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.IsFollowingRequest;
import edu.byu.cs.tweeter.model.network.response.IsFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.Response;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowing extends AuthenticatedTask
{

    public static final String IS_FOLLOWING_KEY = "is-following";

    /**
     * The alleged follower.
     */
    private final User first;

    /**
     * The alleged followee.
     */
    private final User second;

    private boolean isFollowing;

    public IsFollowing(AuthToken authToken, User first, User second, Handler messageHandler)
    {
        super(authToken, messageHandler);
        this.first = first;
        this.second = second;
    }

    @Override
    protected Response runTask() throws IOException, TweeterRemoteException
    {


        IsFollowingRequest request = new IsFollowingRequest();
        IsFollowingResponse response = (IsFollowingResponse) getResponse(request, IsFollowingRequest.class);

        isFollowing = response.isFollowing();

        return response;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle)
    {
        msgBundle.putBoolean(IS_FOLLOWING_KEY, isFollowing);
    }
}
