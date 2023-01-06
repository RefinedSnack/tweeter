package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.IsFollowingRequest;
import edu.byu.cs.tweeter.model.network.response.IsFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.Response;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowingTask extends AuthenticatedTask
{

    public static final String IS_FOLLOWING_KEY = "is-following";

    /**
     * The alleged follower.
     */
    private final String followerAlias;

    /**
     * The alleged followee.
     */
    private final String followeeAlias;

    private boolean isFollowing;

    public IsFollowingTask(AuthToken authToken, String followerAlias, String followeeAlias, Handler messageHandler)
    {
        super(authToken, messageHandler);
        this.followerAlias = followerAlias;
        this.followeeAlias = followeeAlias;
    }

    @Override
    protected Response runTask() throws IOException, TweeterRemoteException
    {
        IsFollowingRequest request = new IsFollowingRequest(getAuthToken(), followerAlias, followeeAlias);
        IsFollowingResponse response = (IsFollowingResponse) getResponse(request, IsFollowingRequest.class);

        isFollowing = response.isFollowing();

        return response;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle)
    {
        msgBundle.putBoolean(IS_FOLLOWING_KEY, isFollowing);
    }

    protected String getFollowerAlias()
    {
        return followerAlias;
    }

    protected String getFolloweeAlias()
    {
        return followeeAlias;
    }

    protected boolean isFollowing()
    {
        return isFollowing;
    }
}
