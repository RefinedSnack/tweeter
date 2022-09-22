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

public class MainPresenter
        implements
        MainService.PostStatusObserver,
        MainService.UnfollowObserver,
        MainService.FollowObserver,
        MainService.IsFollowerObserver,
        MainService.GetFollowingObserver,
        MainService.GetFollowersObserver,
        MainService.LogoutObserver
{
    public interface MainView
    {
        void navigateToUser(User user);

        void displayPostingInfoMessage(String message);

        void clearPostingInfoMessage();

        void displayLogoutInfoMessage(String message);

        void clearLogoutInfoMessage();

        void displayInfoMessage(String message);

        void updateSelectedUserFollowingAndFollowers();

        void updateFollowButton(boolean isFollowing);

        void setFollowButtonEnabled(boolean value);

        void setFolloweeCount(int count);

        void setFollowerCount(int count);

        void logoutUser();
    }

    MainView view;

    public MainPresenter(MainView view)
    {
        this.view = view;
    }

    public void postStatus(AuthToken authToken, String post, User currUser)
    {
        view.displayPostingInfoMessage("Posting Status...");
        String postTime = getFormattedDateTime();
        if (postTime == null)
        {
            return;
        }
        Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), postTime, parseURLs(post), parseMentions(post));
        new MainService().postStatus(authToken, newStatus, this);
    }

    @Override
    public void handlePostMessageSuccess()
    {
        view.clearPostingInfoMessage();
        view.displayInfoMessage("Successfully Posted!");
    }

    @Override
    public void handlePostMessageFailure(String message)
    {
        view.displayInfoMessage("Failed to post status: " + message);
    }

    @Override
    public void handlePostMessageThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to post status because of exception: " + ex.getMessage());
    }


    public void unfollow(AuthToken authToken, User user)
    {
        view.setFollowButtonEnabled(false);
        new MainService().unfollow(authToken, user, this);
        view.displayInfoMessage("Removing " + user.getName() + "...");
    }

    @Override
    public void handleUnfollowSuccess()
    {
        view.updateSelectedUserFollowingAndFollowers();
        view.updateFollowButton(false);
    }

    @Override
    public void handleUnfollowFailure(String message)
    {
        view.displayInfoMessage("Failed to unfollow: " + message);
    }

    @Override
    public void handleUnfollowThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to unfollow because of exception: " + ex.getMessage());
    }

    public void follow(AuthToken authToken, User user)
    {
        view.setFollowButtonEnabled(false);
        new MainService().follow(authToken, user, this);
        view.displayInfoMessage("Adding " + user.getName() + "...");
    }

    @Override
    public void handleFollowSuccess()
    {
        view.updateSelectedUserFollowingAndFollowers();
        view.updateFollowButton(true);
    }

    @Override
    public void handleFollowFailure(String message)
    {
        view.displayInfoMessage("Failed to follow: " + message);
    }

    @Override
    public void handleFollowThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to follow because of exception: " + ex.getMessage());
    }

    public void isFollowing(AuthToken authToken, User currUser, User selectedUser)
    {
        new MainService().IsFollower(authToken, currUser, selectedUser, this);
    }

    @Override
    public void handleIsFollowerSuccess(boolean isFollowing)
    {
        view.updateFollowButton(isFollowing);
    }

    @Override
    public void handleFollowerFailure(String message)
    {
        view.displayInfoMessage("Failed to determine following relationship: " + message);
    }

    @Override
    public void handleFollowerThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to determine following relationship because of exception: " + ex.getMessage());
    }


    public void getFollowCounts(AuthToken currUserAuthToken, User currUser, User selectedUser)
    {
        new MainService().getFollowCounts(currUserAuthToken, currUser, selectedUser, this, this);
    }

    @Override
    public void handleGetFollowingSuccess(int count)
    {
        view.setFolloweeCount(count);
    }

    @Override
    public void handleGetFollowingFailure(String message)
    {
        view.displayInfoMessage("Failed to get following count: " + message);
    }

    @Override
    public void handleGetFollowingThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to get following count because of exception: " + ex.getMessage());
    }


    @Override
    public void handleGetFollowersSuccess(int count)
    {
        view.setFollowerCount(count);
    }

    @Override
    public void handleGetFollowersFailure(String message)
    {
        view.displayInfoMessage("Failed to get follower count: " + message);
    }

    @Override
    public void handleGetFollowersThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to get follower count because of exception: " + ex.getMessage());
    }

    public void logout(AuthToken authToken)
    {
        view.displayLogoutInfoMessage("Logging Out...");
        new MainService().logout(authToken, this);
    }

    @Override
    public void handleLogoutSuccess()
    {
        view.clearLogoutInfoMessage();
        view.logoutUser();
    }

    @Override
    public void handleLogoutFailure(String message)
    {
        view.displayInfoMessage("Failed to logout: " + message);
    }

    @Override
    public void handleLogoutThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to logout because of exception: " + ex.getMessage());
    }




























    // @Overrides that apply to multiple observers
    @Override
    public void enableFollowButton()
    {
        view.setFollowButtonEnabled(true);
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
            handlePostMessageThrewAnException(ex);
            return null;
        }
    }
}
