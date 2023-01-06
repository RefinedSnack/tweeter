package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.model.network.response.Response;
import edu.byu.cs.tweeter.model.network.response.UnfollowUserResponse;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowUserTask extends AuthenticatedTask
{

    /**
     * The user that is being followed.
     */
    private final String followee;

    public UnfollowUserTask(AuthToken authToken, String followee, Handler messageHandler)
    {
        super(authToken, messageHandler);
        this.followee = followee;
    }

    @Override
    protected Response runTask() throws IOException, TweeterRemoteException
    {
        UnfollowUserRequest request = new UnfollowUserRequest(getAuthToken(), getFollowee());
        UnfollowUserResponse response = (UnfollowUserResponse) getResponse(request, UnfollowUserRequest.class);
        return response;
    }

    public String getFollowee()
    {
        return followee;
    }
}
