package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowingService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter implements UserService.GetUserObserver, FollowingService.GetFollowingObserver
{
    public interface FollowingView
    {
        void displayInfoMessage(String message);

        void navigateToUser(User user);

        void setLoading(boolean value);

        void removeLoadingFooter();

        void setLast(User last);

        void addItems(List<User> toAdd);

        void setHasMorePages(boolean value);
    }

    private FollowingView view;
    public FollowingPresenter(FollowingView view)
    {
        this.view = view;
    }

    public void getFollowing(AuthToken authToken, User user, int pageSize, User last)
    {
        new FollowingService().getFollowing(authToken, user, pageSize, last, this);
    }

    @Override
    public void handleGetFollowingSuccess(Boolean hasMorePages, User last, List<User> toAdd)
    {
        view.setHasMorePages(hasMorePages);
        view.setLast(last);
        view.addItems(toAdd);
    }

    @Override
    public void handleGetFollowingFailure(String message)
    {
        view.displayInfoMessage("Failed to get following: " + message);
    }

    @Override
    public void handleGetFollowingThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to get following because of exception: " + ex.getMessage());
    }

    @Override
    public void handleLoading()
    {
        view.setLoading(false);
        view.removeLoadingFooter();
    }

    public void getUser(AuthToken authToken, String alias)
    {
        view.displayInfoMessage("Getting user's profile...");
        new UserService().getUser(authToken, alias, this);
    }

    @Override
    public void handleGetUserSuccess(User user)
    {
        view.navigateToUser(user);
    }

    @Override
    public void handleGetUserFailure(String message)
    {
        view.displayInfoMessage("Failed to get user's profile: " + message);
    }

    @Override
    public void handleGetUserThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to get user's profile because of exception: " + ex.getMessage());
    }


}
