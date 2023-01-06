package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.LogoutRequest;
import edu.byu.cs.tweeter.model.network.response.LogoutResponse;
import edu.byu.cs.tweeter.model.network.response.Response;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AuthenticatedTask
{

    public LogoutTask(AuthToken authToken, Handler messageHandler)
    {
        super(authToken, messageHandler);
    }

    @Override
    protected Response runTask() throws IOException, TweeterRemoteException
    {
        LogoutRequest request = new LogoutRequest(getAuthToken());
        LogoutResponse response = (LogoutResponse) getResponse(request, LogoutRequest.class);
        return response;
    }
}
