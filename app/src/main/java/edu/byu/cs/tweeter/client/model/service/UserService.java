package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
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

    private class AuthenticationHandler extends BackgroundTaskHandler<AuthenticationObserver>
    {
        public AuthenticationHandler(AuthenticationObserver observer)
        {
            super(observer);
        }

        @Override
        protected void handleSuccessMessage(AuthenticationObserver observer, Bundle data)
        {
            User loggedInUser = (User) data.getSerializable(LoginTask.USER_KEY);
            AuthToken authToken = (AuthToken) data.getSerializable(LoginTask.AUTH_TOKEN_KEY);

            // Cache user session information
            Cache.getInstance().setCurrUser(loggedInUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);
            observer.handleSuccess(loggedInUser, authToken);
        }

    }

    public interface GetUserObserver
    {
        void handleGetUserSuccess(User user);

        void handleGetUserFailure(String message);

        void handleGetUserThrewAnException(Exception ex);
    }



    private class GetUserHandler extends Handler
    {
        GetUserObserver observer;

        public GetUserHandler(GetUserObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg)
        {
            boolean success = msg.getData().getBoolean(GetUserTask.SUCCESS_KEY);
            if (success)
            {
                User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
                observer.handleGetUserSuccess(user);
            } else if (msg.getData().containsKey(GetUserTask.MESSAGE_KEY))
            {
                String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);
                observer.handleGetUserFailure(message);
            } else if (msg.getData().containsKey(GetUserTask.EXCEPTION_KEY))
            {
                Exception ex = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);
                observer.handleGetUserThrewAnException(ex);
            }
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
