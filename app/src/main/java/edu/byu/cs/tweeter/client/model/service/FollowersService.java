package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersService
{
    public interface GetFollowersObserver
    {
        void handleGetFollowersSuccess(Boolean hasMorePages, User last, List<User> toAdd);
        void handleGetFollowersFailure(String message);
        void handleGetFollowersThrewAnException(Exception ex);
        void handleLoading();
    }

    public void getFollowers(AuthToken authToken, User user, int pageSize, User last, GetFollowersObserver observer)
    {
        GetFollowersTask getFollowersTask = new GetFollowersTask(authToken,
                user, pageSize, last, new GetFollowersHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }
    private class GetFollowersHandler extends Handler {
        private GetFollowersObserver observer;

        public GetFollowersHandler(GetFollowersObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            observer.handleLoading();

            boolean success = msg.getData().getBoolean(GetFollowersTask.SUCCESS_KEY);
            if (success) {
                List<User> toAdd = (List<User>) msg.getData().getSerializable(GetFollowersTask.FOLLOWERS_KEY);
                Boolean hasMorePages = msg.getData().getBoolean(GetFollowersTask.MORE_PAGES_KEY);

                User last = (toAdd.size() > 0) ? toAdd.get(toAdd.size() - 1) : null;

                observer.handleGetFollowersSuccess(hasMorePages, last, toAdd);
            } else if (msg.getData().containsKey(GetFollowersTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowersTask.MESSAGE_KEY);
                observer.handleGetFollowersFailure(message);
            } else if (msg.getData().containsKey(GetFollowersTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFollowersTask.EXCEPTION_KEY);
                observer.handleGetFollowersThrewAnException(ex);
            }
        }
    }
}
