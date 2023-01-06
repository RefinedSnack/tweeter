package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class NavigateToUserPresenter<VIEW extends NavigateToUserPresenter.NavigateToUserView> extends Presenter<VIEW>
{
    public interface NavigateToUserView extends Presenter.View
    {
        void navigateToUser(String user);
    }

    public NavigateToUserPresenter(VIEW view)
    {
        super(view);
    }


    public void getUser(AuthToken authToken, String alias)
    {
        view.displayInfoMessage("Getting user's profile...");
        new UserService().getUser(authToken, alias, new GetUserObserver());
    }

    protected class GetUserObserver
            extends InfixErrorObserver
            implements UserService.GetUserObserver
    {
        @Override
        public void handleSuccess(String user)
        {
            view.navigateToUser(user);
        }

        @Override
        protected String infixValue()
        {
            return "get user\'s profile";
        }
    }
}
