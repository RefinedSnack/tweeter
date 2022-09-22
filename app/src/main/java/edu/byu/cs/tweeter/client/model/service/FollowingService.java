package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingService
{
    public interface GetFollowingObserver
    {
        void handleGetFollowingSuccess(Boolean hasMorePages, User last, List<User> toAdd);
        void handleGetFollowingFailure(String message);
        void handleGetFollowingThrewAnException(Exception ex);
        void handleLoading();
    }

    public void getFollowing(AuthToken authToken, User user, int pageSize, User last, GetFollowingObserver observer)
    {
        GetFollowingTask getFollowingTask = new GetFollowingTask(authToken,
                user, pageSize, last, new GetFollowingHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }
    private class GetFollowingHandler extends Handler {
        private GetFollowingObserver observer;

        public GetFollowingHandler(GetFollowingObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            observer.handleLoading();

            boolean success = msg.getData().getBoolean(GetFollowingTask.SUCCESS_KEY);
            if (success) {
                List<User> toAdd = (List<User>) msg.getData().getSerializable(GetFollowingTask.FOLLOWEES_KEY);
                Boolean hasMorePages = msg.getData().getBoolean(GetFollowingTask.MORE_PAGES_KEY);

                User last = (toAdd.size() > 0) ? toAdd.get(toAdd.size() - 1) : null;

                observer.handleGetFollowingSuccess(hasMorePages, last, toAdd);
            } else if (msg.getData().containsKey(GetFollowingTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowingTask.MESSAGE_KEY);
                observer.handleGetFollowingFailure(message);
            } else if (msg.getData().containsKey(GetFollowingTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFollowingTask.EXCEPTION_KEY);
                observer.handleGetFollowingThrewAnException(ex);
            }
        }
    }
}
