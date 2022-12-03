package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticationPresenter
{
    public interface AuthenticationView
    {
        void displayErrorMessage(String message);

        void clearErrorMessage();

        void displayInfoMessage(String message);

        void clearInfoMessage();

        void navigateToUser(User user);
    }

    protected AuthenticationView view;


    public AuthenticationPresenter(AuthenticationView view)
    {
        this.view = view;
    }

    protected class AuthenticationObserver implements UserService.AuthenticationObserver
    {
        @Override
        public void handleSuccess(User user, AuthToken authToken)
        {
            view.clearInfoMessage();
            view.clearErrorMessage();

            view.displayInfoMessage("Hello " + user.getName());
            view.navigateToUser(user);
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage(String.format("Failed to %s: %s",
                                        getMessagePrefix(),
                                        message));
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage(String.format("Failed to %s because of exception: %s",
                                        getMessagePrefix(),
                                        ex.getMessage()));
        }
    }

    protected abstract String getMessagePrefix();
}
