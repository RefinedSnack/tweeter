package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainService
{
    public interface PostStatusObserver
    {
        void handlePostMessageSuccess();

        void handlePostMessageFailure(String message);

        void handlePostMessageThrewAnException(Exception ex);
    }

    public void postStatus(AuthToken authToken, Status newStatus, PostStatusObserver observer)
    {
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new PostStatusHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(statusTask);
    }

    private class PostStatusHandler extends Handler
    {
        private PostStatusObserver observer;

        public PostStatusHandler(PostStatusObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg)
        {
            boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
            if (success)
            {
                observer.handlePostMessageSuccess();
            } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY))
            {
                String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);
                observer.handlePostMessageFailure(message);
            } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY))
            {
                Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
                observer.handlePostMessageThrewAnException(ex);
            }
        }
    }

    public interface UnfollowObserver
    {
        void handleUnfollowSuccess();

        void handleUnfollowFailure(String message);

        void handleUnfollowThrewAnException(Exception ex);

        void enableFollowButton();
    }

    public void unfollow(AuthToken authToken, User user, UnfollowObserver observer)
    {
        UnfollowTask unfollowTask = new UnfollowTask(authToken, user, new UnfollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }

    // UnfollowHandler
    private class UnfollowHandler extends Handler
    {
        private UnfollowObserver observer;

        public UnfollowHandler(UnfollowObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg)
        {
            boolean success = msg.getData().getBoolean(UnfollowTask.SUCCESS_KEY);
            if (success)
            {
                observer.handleUnfollowSuccess();
            } else if (msg.getData().containsKey(UnfollowTask.MESSAGE_KEY))
            {
                String message = msg.getData().getString(UnfollowTask.MESSAGE_KEY);
                observer.handleUnfollowFailure(message);
            } else if (msg.getData().containsKey(UnfollowTask.EXCEPTION_KEY))
            {
                Exception ex = (Exception) msg.getData().getSerializable(UnfollowTask.EXCEPTION_KEY);
                observer.handleUnfollowThrewAnException(ex);
            }
            observer.enableFollowButton();
        }
    }

    public interface FollowObserver
    {
        void handleFollowSuccess();

        void handleFollowFailure(String message);

        void handleFollowThrewAnException(Exception ex);

        void enableFollowButton();
    }

    public void follow(AuthToken authToken, User user, FollowObserver observer)
    {
        FollowTask followTask = new FollowTask(authToken, user, new FollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }

    private class FollowHandler extends Handler
    {
        private FollowObserver observer;

        public FollowHandler(FollowObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg)
        {
            boolean success = msg.getData().getBoolean(FollowTask.SUCCESS_KEY);
            if (success)
            {
                observer.handleFollowSuccess();
            } else if (msg.getData().containsKey(FollowTask.MESSAGE_KEY))
            {
                String message = msg.getData().getString(FollowTask.MESSAGE_KEY);
                observer.handleFollowFailure(message);
            } else if (msg.getData().containsKey(FollowTask.EXCEPTION_KEY))
            {
                Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
                observer.handleFollowThrewAnException(ex);
            }
            observer.enableFollowButton();


        }
    }

    public interface IsFollowerObserver
    {
        void handleIsFollowerSuccess(boolean isFollower);

        void handleFollowerFailure(String message);

        void handleFollowerThrewAnException(Exception ex);
    }

    public void IsFollower(AuthToken authToken, User currUser, User selectedUser, IsFollowerObserver observer)
    {
        IsFollowerTask isFollowerTask = new IsFollowerTask(authToken, currUser, selectedUser, new IsFollowerHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

    // IsFollowerHandler

    private class IsFollowerHandler extends Handler
    {
        IsFollowerObserver observer;

        public IsFollowerHandler(IsFollowerObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg)
        {
            boolean success = msg.getData().getBoolean(IsFollowerTask.SUCCESS_KEY);
            if (success)
            {
                boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);

                // If logged in user if a follower of the selected user, display the follow button as "following"
                observer.handleIsFollowerSuccess(isFollower);
            } else if (msg.getData().containsKey(IsFollowerTask.MESSAGE_KEY))
            {
                String message = msg.getData().getString(IsFollowerTask.MESSAGE_KEY);
                observer.handleFollowerFailure(message);
            } else if (msg.getData().containsKey(IsFollowerTask.EXCEPTION_KEY))
            {
                Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
                observer.handleFollowerThrewAnException(ex);
            }
        }
    }


    public interface GetFollowingObserver
    {
        void handleGetFollowingSuccess(int count);

        void handleGetFollowingFailure(String message);

        void handleGetFollowingThrewAnException(Exception ex);
    }

    private void getFollowingCount(AuthToken authToken, User currUser, User selectedUser, GetFollowingObserver observer, ExecutorService executor)
    {
        GetFollowingCountTask followersCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowingCountHandler(observer));
        executor.execute(followersCountTask);
    }

    // GetFollowingCountHandler

    private class GetFollowingCountHandler extends Handler
    {
        private GetFollowingObserver observer;

        public GetFollowingCountHandler(GetFollowingObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg)
        {
            boolean success = msg.getData().getBoolean(GetFollowingCountTask.SUCCESS_KEY);
            if (success)
            {
                int count = msg.getData().getInt(GetFollowingCountTask.COUNT_KEY);
                observer.handleGetFollowingSuccess(count);
            } else if (msg.getData().containsKey(GetFollowingCountTask.MESSAGE_KEY))
            {
                String message = msg.getData().getString(GetFollowingCountTask.MESSAGE_KEY);
                observer.handleGetFollowingFailure(message);
            } else if (msg.getData().containsKey(GetFollowingCountTask.EXCEPTION_KEY))
            {
                Exception ex = (Exception) msg.getData().getSerializable(GetFollowingCountTask.EXCEPTION_KEY);
                observer.handleGetFollowingThrewAnException(ex);
            }
        }
    }

    public interface GetFollowersObserver
    {
        void handleGetFollowersSuccess(int count);

        void handleGetFollowersFailure(String message);

        void handleGetFollowersThrewAnException(Exception ex);
    }

    private void getFollowersCount(AuthToken authToken, User currUser, User selectedUser, GetFollowersObserver observer, ExecutorService executor)
    {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowersCountHandler(observer));
        executor.execute(followersCountTask);
    }

    // GetFollowersCountHandler

    private class GetFollowersCountHandler extends Handler
    {
        private GetFollowersObserver observer;

        public GetFollowersCountHandler(GetFollowersObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg)
        {
            boolean success = msg.getData().getBoolean(GetFollowersCountTask.SUCCESS_KEY);
            if (success)
            {
                int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
                observer.handleGetFollowersSuccess(count);
            } else if (msg.getData().containsKey(GetFollowersCountTask.MESSAGE_KEY))
            {
                String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
                observer.handleGetFollowersFailure(message);
            } else if (msg.getData().containsKey(GetFollowersCountTask.EXCEPTION_KEY))
            {
                Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
                observer.handleGetFollowersThrewAnException(ex);
            }
        }
    }

    public void getFollowCounts(AuthToken authToken, User currUser, User selectedUser, GetFollowingObserver followingObserver, GetFollowersObserver followersObserver)
    {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        getFollowingCount(authToken, currUser, selectedUser, followingObserver, executor);
        getFollowersCount(authToken, currUser, selectedUser, followersObserver, executor);
    }
}
