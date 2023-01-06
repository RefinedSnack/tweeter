package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class MainService
{
    public interface SimpleServiceObserver extends ServiceObserver
    {
        void handleSuccess();
    }

    public interface IsFollowerObserver extends ServiceObserver
    {
        void handleSuccess(boolean isFollower);
    }

    public interface GetCountObserver extends ServiceObserver
    {
        void handleSuccess(int count);
    }

    private class SimpleServiceHandler extends BackgroundTaskHandler<SimpleServiceObserver>
    {
        public SimpleServiceHandler(SimpleServiceObserver observer)
        {
            super(observer);
        }

        @Override
        protected void handleSuccessMessage(SimpleServiceObserver observer, Bundle data)
        {
            observer.handleSuccess();
        }
    }

    private class IsFollowerHandler extends BackgroundTaskHandler<IsFollowerObserver>
    {
        public IsFollowerHandler(IsFollowerObserver observer)
        {
            super(observer);
        }

        @Override
        protected void handleSuccessMessage(IsFollowerObserver observer, Bundle data)
        {
            boolean isFollowing = data.getBoolean(IsFollowingTask.IS_FOLLOWING_KEY);
            observer.handleSuccess(isFollowing);
        }
    }

    private class GetCountHandler extends BackgroundTaskHandler<GetCountObserver>
    {

        public GetCountHandler(GetCountObserver observer)
        {
            super(observer);
        }

        @Override
        protected void handleSuccessMessage(GetCountObserver observer, Bundle data)
        {
            int count = data.getInt(GetCountTask.COUNT_KEY);
            observer.handleSuccess(count);
        }
    }

    public void postStatus(AuthToken authToken, Status newStatus, SimpleServiceObserver observer)
    {
        BackgroundTaskUtils.runTask(new PostStatusTask(authToken, newStatus, new SimpleServiceHandler(observer)));
    }

    public void unfollow(AuthToken authToken, String user, SimpleServiceObserver observer)
    {
        BackgroundTaskUtils.runTask(new UnfollowUserTask(authToken, user, new SimpleServiceHandler(observer)));

    }

    public void follow(AuthToken authToken, String user, SimpleServiceObserver observer)
    {
        BackgroundTaskUtils.runTask(new FollowUserTask(authToken, user, new SimpleServiceHandler(observer)));
    }

    public void IsFollowing(AuthToken authToken, String currUser, String selectedUser, IsFollowerObserver observer)
    {
        BackgroundTaskUtils.runTask(new IsFollowingTask(authToken, currUser, selectedUser, new IsFollowerHandler(observer)));
    }

    private void getFollowingCount(AuthToken authToken, String selectedUser, GetCountObserver observer, ExecutorService executor)
    {
        BackgroundTaskUtils.runTask(new GetFollowingCountTask(authToken, selectedUser, new GetCountHandler(observer)), executor);
    }

    private void getFollowersCount(AuthToken authToken, String selectedUser, GetCountObserver observer, ExecutorService executor)
    {
        BackgroundTaskUtils.runTask(new GetFollowersCountTask(authToken, selectedUser, new GetCountHandler(observer)), executor);
    }

    public void getFollowCounts(AuthToken authToken, String selectedUser, GetCountObserver followingObserver, GetCountObserver followersObserver)
    {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        getFollowingCount(authToken, selectedUser, followingObserver, executor);
        getFollowersCount(authToken, selectedUser, followersObserver, executor);
    }

    public void logout(AuthToken authToken, SimpleServiceObserver observer)
    {
        LogoutTask logoutTask = new LogoutTask(authToken, new SimpleServiceHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }

}
