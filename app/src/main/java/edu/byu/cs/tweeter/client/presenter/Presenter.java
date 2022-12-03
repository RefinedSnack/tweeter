package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class Presenter <VIEW extends Presenter.view>
{
    public interface view
    {
        void displayInfoMessage(String message);
        void navigateToUser(User user);
    }
    protected VIEW view;

    public Presenter(VIEW view)
    {
        this.view = view;
    }

    public void getUser(AuthToken authToken, String alias)
    {
        view.displayInfoMessage("Getting user's profile...");
        new UserService().getUser(authToken, alias, new GetUserObserver());
    }

    protected class GetUserObserver implements UserService.GetUserObserver
    {
        @Override
        public void handleSuccess(User user)
        {
            view.navigateToUser(user);
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage("Failed to get user's profile: " + message);
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage("Failed to get user's profile because of exception: " + ex.getMessage());
        }
    }
}
