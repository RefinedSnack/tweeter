package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService
{
    public interface AuthenticationObserver extends ServiceObserver
    {
        void handleSuccess(User user, AuthToken token);
    }

    public interface GetUserObserver extends ServiceObserver
    {
        void handleSuccess(User user);
    }

    private class AuthenticationHandler extends BackgroundTaskHandler<AuthenticationObserver>
    {
        public AuthenticationHandler(AuthenticationObserver observer)
        {
            super(observer);
        }

        @Override
        protected void handleSuccessMessage(AuthenticationObserver observer, Bundle data)
        {
            User loggedInUser = (User) data.getSerializable(AuthenticateTask.USER_KEY);
            AuthToken authToken = (AuthToken) data.getSerializable(AuthenticateTask.AUTH_TOKEN_KEY);

            // Cache user session information
            Cache.getInstance().setCurrUser(loggedInUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);
            observer.handleSuccess(loggedInUser, authToken);
        }
    }

    private class GetUserHandler extends BackgroundTaskHandler<GetUserObserver>
    {

        public GetUserHandler(GetUserObserver observer)
        {
            super(observer);
        }

        @Override
        protected void handleSuccessMessage(GetUserObserver observer, Bundle data)
        {
            User user = (User) data.getSerializable(GetUserTask.USER_KEY);
            observer.handleSuccess(user);
        }
    }

    public void login(String alias, String password, AuthenticationObserver observer)
    {
        LoginTask loginTask = new LoginTask(alias, password, new AuthenticationHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64, AuthenticationObserver observer)
    {
        RegisterTask registerTask = new RegisterTask(firstName, lastName, alias, password, imageBytesBase64, new AuthenticationHandler(observer));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(registerTask);
    }

    public void getUser(AuthToken authToken, String alias, GetUserObserver observer)
    {
        GetUserTask getUserTask = new GetUserTask(authToken, alias, new GetUserHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }


}
