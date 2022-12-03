package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter implements UserService.GetUserObserver, FeedService.GetFeedObserver
{
    public interface FeedView
    {
        void displayInfoMessage(String message);

        void navigateToUser(User user);

        void setLoading(boolean value);

        void removeLoadingFooter();

        void setLast(Status last);

        void addItems(List<Status> toAdd);

        void setHasMorePages(boolean value);

        void addLoadingFooter();
    }

    private FeedView view;
    public FeedPresenter(FeedView view)
    {
        this.view = view;
    }

    public void getFeed(AuthToken authToken, User user, int pageSize, Status last)
    {
        new FeedService().getFeed(authToken, user, pageSize, last, this);
    }

    @Override
    public void handleGetFeedSuccess(Boolean hasMorePages, Status last, List<Status> toAdd)
    {
        view.setHasMorePages(hasMorePages);
        view.setLast(last);
        view.addItems(toAdd);
    }

    @Override
    public void handleGetFeedFailure(String message)
    {
        view.displayInfoMessage("Failed to get feed: " + message);
    }

    @Override
    public void handleGetFeedThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to get feed because of exception: " + ex.getMessage());
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
