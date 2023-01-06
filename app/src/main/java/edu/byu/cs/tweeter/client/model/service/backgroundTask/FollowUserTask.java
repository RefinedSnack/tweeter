package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.FollowUserRequest;
import edu.byu.cs.tweeter.model.network.response.FollowUserResponse;
import edu.byu.cs.tweeter.model.network.response.Response;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowUserTask extends AuthenticatedTask
{
    /**
     * The user that is being followed.
     */
    private final String followee;

    public FollowUserTask(AuthToken authToken, String followee, Handler messageHandler)
    {
        super(authToken, messageHandler);
        this.followee = followee;
    }

    @Override
    protected Response runTask() throws IOException, TweeterRemoteException
    {
        FollowUserRequest request = new FollowUserRequest(getAuthToken(), followee);
        FollowUserResponse response = (FollowUserResponse) getResponse(request, FollowUserRequest.class);
        return response;
    }


}
