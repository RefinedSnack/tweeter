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

public class MainPresenter extends Presenter<MainPresenter.MainView>
{
    public interface MainView extends Presenter.view
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
        new MainService().postStatus(authToken, newStatus, new PostStatusObserver());
    }

    private class PostStatusObserver implements MainService.SimpleServiceObserver
    {
        @Override
        public void handleSuccess()
        {
            view.clearPostingInfoMessage();
            view.displayInfoMessage("Successfully Posted!");
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage("Failed to post status: " + message);
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage("Failed to post status because of exception: " + ex.getMessage());
        }
    }


    public void unfollow(AuthToken authToken, User user)
    {
        view.setFollowButtonEnabled(true);
        new MainService().unfollow(authToken, user, new UnfollowObserver());
        view.displayInfoMessage("Removing " + user.getName() + "...");
    }

    private class UnfollowObserver implements MainService.SimpleServiceObserver
    {
        @Override
        public void handleSuccess()
        {
            view.updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(false);
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage("Failed to unfollow: " + message);
            view.updateFollowButton(true);
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage("Failed to unfollow because of exception: " + ex.getMessage());
            view.updateFollowButton(true);
        }
    }

    public void follow(AuthToken authToken, User user)
    {
        view.setFollowButtonEnabled(false);
        new MainService().follow(authToken, user, new FollowObserver());
        view.displayInfoMessage("Adding " + user.getName() + "...");
    }

    private class FollowObserver implements MainService.SimpleServiceObserver
    {
        @Override
        public void handleSuccess()
        {
            view.updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(true);
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage("Failed to follow: " + message);
            view.updateFollowButton(false);
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage("Failed to follow because of exception: " + ex.getMessage());
            view.updateFollowButton(false);
        }
    }

    public void isFollowing(AuthToken authToken, User currUser, User selectedUser)
    {
        new MainService().IsFollower(authToken, currUser, selectedUser, new IsFollowingObserver());
    }

    private class IsFollowingObserver implements MainService.IsFollowerObserver
    {
        @Override
        public void handleSuccess(boolean isFollowing)
        {
            view.updateFollowButton(isFollowing);
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage("Failed to determine following relationship: " + message);
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage("Failed to determine following relationship because of exception: " + ex.getMessage());
        }

    }


    public void getFollowCounts(AuthToken currUserAuthToken, User currUser, User selectedUser)
    {
        new MainService().getFollowCounts(currUserAuthToken, selectedUser, new FollowingObserver(), new FollowersObserver());
    }

    private class FollowingObserver implements MainService.GetCountObserver
    {
        @Override
        public void handleSuccess(int count)
        {
            view.setFolloweeCount(count);
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage("Failed to get following count: " + message);
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage("Failed to get following count because of exception: " + ex.getMessage());
        }
    }

    private class FollowersObserver implements MainService.GetCountObserver
    {
        @Override
        public void handleSuccess(int count)
        {
            view.setFollowerCount(count);
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage("Failed to get follower count: " + message);
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage("Failed to get follower count because of exception: " + ex.getMessage());
        }
    }

    public void logout(AuthToken authToken)
    {
        view.displayLogoutInfoMessage("Logging Out...");
        new MainService().logout(authToken, new LogoutObserver());
    }

    private class LogoutObserver implements MainService.SimpleServiceObserver
    {
        @Override
        public void handleSuccess()
        {
            view.clearLogoutInfoMessage();
            view.logoutUser();
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage("Failed to logout: " + message);
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage("Failed to logout because of exception: " + ex.getMessage());
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

    public int findUrlEndIndex(String word)
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

    public List<String> parseMentions(String post)
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

    public String getFormattedDateTime()
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
