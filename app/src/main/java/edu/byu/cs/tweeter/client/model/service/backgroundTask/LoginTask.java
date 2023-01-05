package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.LoginRequest;
import edu.byu.cs.tweeter.model.network.response.LoginResponse;


/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticateTask
{

    public LoginTask(String username, String password, Handler messageHandler)
    {
        super(messageHandler, username, password);
    }

    @Override
    protected LoginResponse runAuthenticationTask() throws IOException, TweeterRemoteException
    {
        LoginRequest request = new LoginRequest(username, password);
        return getServerFacade().login(request);
    }
}
