package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.GetUserRequest;
import edu.byu.cs.tweeter.model.network.response.GetUserResponse;
import edu.byu.cs.tweeter.model.network.response.Response;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthenticatedTask
{

    public static final String USER_KEY = "user";

    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private final String alias;

    private User user;

    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler)
    {
        super(authToken, messageHandler);
        this.alias = alias;
    }

    @Override
    protected Response runTask() throws IOException, TweeterRemoteException
    {
        GetUserRequest request = new GetUserRequest(getAuthToken(), this.alias);
        GetUserResponse response = (GetUserResponse) getResponse(request, GetUserRequest.class);
        user = response.getUser();
        return response;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle)
    {
        msgBundle.putSerializable(USER_KEY, user);
    }

    protected String getAlias()
    {
        return alias;
    }
}
