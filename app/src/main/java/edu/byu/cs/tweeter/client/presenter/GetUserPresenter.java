package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class GetUserPresenter<VIEW extends GetUserPresenter.GetUserView> extends Presenter<VIEW>
{
    public interface GetUserView extends Presenter.View
    {
        void navigateToUser(User user);
    }

    public GetUserPresenter(VIEW view)
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
        public void handleSuccess(User user)
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
