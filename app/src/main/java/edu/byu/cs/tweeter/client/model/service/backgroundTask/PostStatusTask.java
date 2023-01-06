package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.network.response.Response;
import edu.byu.cs.tweeter.model.network.response.PostStatusResponse;

/**
 * Background task that posts a new status sent by a user.
 */
public class PostStatusTask extends AuthenticatedTask
{

    /**
     * The new status being sent. Contains all properties of the status,
     * including the identity of the user sending the status.
     */
    private final Status status;

    public PostStatusTask(AuthToken authToken, Status status, Handler messageHandler)
    {
        super(authToken, messageHandler);
        this.status = status;
    }

    @Override
    protected Response runTask() throws IOException, TweeterRemoteException
    {
        PostStatusRequest request = new PostStatusRequest(getAuthToken(), status);
        PostStatusResponse response = (PostStatusResponse) getResponse(request, PostStatusRequest.class);
        return response;
    }

    public Status getStatus()
    {
        return status;
    }
}
