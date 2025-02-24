package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.MainService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends NavigateToUserPresenter<MainPresenter.MainView>
{
    public interface MainView extends NavigateToUserView
    {
        void displayPostingInfoMessage(String message);

        void clearPostingInfoMessage();

        void displayLogoutInfoMessage(String message);

        void clearLogoutInfoMessage();

        void updateSelectedUserFollowingAndFollowers();

        void updateFollowButton(boolean isFollowing);

        void setFollowButtonEnabled(boolean value);

        void setFolloweeCount(int count);

        void setFollowerCount(int count);

        void logoutUser();
    }

    public MainPresenter(MainView view)
    {
        super(view);
    }

    public void postStatus(AuthToken authToken, String post)
    {
        view.displayPostingInfoMessage("Posting Status...");
        String postTime = getFormattedDateTime();
        if (postTime == null)
        {
            view.displayInfoMessage("Failed to post status, could not retrieve current date and time");
            return;
        }
        Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), postTime, parseURLs(post), parseMentions(post));
        mainServiceFactory().postStatus(authToken, newStatus, PostStatusObserverFactory());
    }
    public MainService mainServiceFactory()
    {
        return new MainService();
    }

    public PostStatusObserver PostStatusObserverFactory()
    {
        return new PostStatusObserver();
    }

    private class PostStatusObserver extends InfixErrorObserver implements MainService.SimpleServiceObserver
    {
        @Override
        public void handleSuccess()
        {
            view.clearPostingInfoMessage();
            view.displayInfoMessage("Successfully Posted!");
        }

        @Override
        protected String infixValue()
        {
            return "post status";
        }
    }


    public void unfollow(AuthToken authToken, User user)
    {
        view.setFollowButtonEnabled(false);
        mainServiceFactory().unfollow(authToken, user.getAlias(), new UnfollowObserver());
        view.displayInfoMessage("Removing " + user.getName() + "...");
    }


    public void follow(AuthToken authToken, User user)
    {
        view.setFollowButtonEnabled(false);
        mainServiceFactory().follow(authToken, user.getAlias(), new FollowObserver());
        view.displayInfoMessage("Adding " + user.getName() + "...");
    }

    private abstract class FollowButtonObserver implements MainService.SimpleServiceObserver
    {
        @Override
        public void handleSuccess()
        {
            view.updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(newButtonState());
            enableButtonToBeClicked();
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage(String.format("Failed to %s: %s", getMessagePrefix(), message));
            enableButtonToBeClicked();
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage(String.format("Failed to %s because of exception: %s", getMessagePrefix(), ex.getMessage()));
            enableButtonToBeClicked();
        }

        private void enableButtonToBeClicked()
        {
            view.setFollowButtonEnabled(true);
        }

        abstract boolean newButtonState();
        abstract String getMessagePrefix();
    }

    private class FollowObserver extends FollowButtonObserver
    {

        @Override
        boolean newButtonState()
        {
            return true;
        }

        @Override
        String getMessagePrefix()
        {
            return "follow";
        }
    }

    private class UnfollowObserver extends FollowButtonObserver
    {

        @Override
        boolean newButtonState()
        {
            return false;
        }

        @Override
        String getMessagePrefix()
        {
            return "unfollow";
        }
    }

    public void isFollowing(AuthToken authToken, User currUser, User selectedUser)
    {
        mainServiceFactory().IsFollowing(authToken, currUser.getAlias(), selectedUser.getAlias(), new IsFollowingObserver());
    }

    private class IsFollowingObserver extends InfixErrorObserver implements MainService.IsFollowerObserver
    {
        @Override
        public void handleSuccess(boolean isFollowing)
        {
            view.updateFollowButton(isFollowing);
        }

        @Override
        protected String infixValue()
        {
            return "determine following relationship";
        }
    }

    public void getFollowCounts(AuthToken currUserAuthToken, User selectedUser)
    {
        mainServiceFactory().getFollowCounts(currUserAuthToken, selectedUser.getAlias(), new FollowingObserver(), new FollowersObserver());
    }

    private class FollowingObserver extends InfixErrorObserver implements MainService.GetCountObserver
    {
        @Override
        public void handleSuccess(int count)
        {
            view.setFolloweeCount(count);
        }

        @Override
        protected String infixValue()
        {
            return "get following count";
        }
    }

    private class FollowersObserver extends InfixErrorObserver implements MainService.GetCountObserver
    {
        @Override
        public void handleSuccess(int count)
        {
            view.setFollowerCount(count);
        }

        @Override
        protected String infixValue()
        {
            return "get follower count";
        }
    }

    public void logout(AuthToken authToken)
    {
        view.displayLogoutInfoMessage("Logging Out...");
        mainServiceFactory().logout(authToken, new LogoutObserver());
    }

    private class LogoutObserver extends InfixErrorObserver implements MainService.SimpleServiceObserver
    {
        @Override
        public void handleSuccess()
        {
            view.clearLogoutInfoMessage();
            view.logoutUser();
        }

        @Override
        protected String infixValue()
        {
            return "logout";
        }
    }

    // helpers
    private List<String> parseURLs(String post)
    {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s"))
        {
            if (word.startsWith("http://") || word.startsWith("https://"))
            {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    private int findUrlEndIndex(String word)
    {
        if (word.contains(".com"))
        {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org"))
        {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu"))
        {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net"))
        {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil"))
        {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else
        {
            return word.length();
        }
    }

    private List<String> parseMentions(String post)
    {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s"))
        {
            if (word.startsWith("@"))
            {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    private String getFormattedDateTime()
    {
        try
        {
            SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

            return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
        } catch (ParseException ex)
        {
            view.displayInfoMessage("Failed to post status because of exception: " + ex.getMessage());
            return null;
        }
    }
}
