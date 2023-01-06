package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticationPresenter extends NavigateToUserPresenter<AuthenticationPresenter.AuthenticationView>
{
    public interface AuthenticationView extends NavigateToUserView
    {
        void displayErrorMessage(String message);

        void clearErrorMessage();

        void clearInfoMessage();
    }


    public AuthenticationPresenter(AuthenticationView view)
    {
        super(view);
    }

    abstract protected class AuthenticationObserver extends InfixErrorObserver implements UserService.AuthenticationObserver
    {
        @Override
        public void handleSuccess(User user, AuthToken authToken)
        {
            view.clearInfoMessage();
            view.clearErrorMessage();

            view.displayInfoMessage("Hello " + user.getName());
            view.navigateToUser(user);
        }


    }
}
