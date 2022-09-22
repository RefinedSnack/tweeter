package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService
{
    public interface GetFeedObserver
    {
        void handleGetFeedSuccess(Boolean user, Status pageSize, List<Status> last);
        void handleGetFeedFailure(String message);
        void handleGetFeedThrewAnException(Exception ex);
        void handleLoading();
    }

    public void getFeed(AuthToken authToken, User user, int pageSize, Status last, GetFeedObserver observer)
    {
        GetFeedTask getFeedTask = new GetFeedTask(authToken,
                user, pageSize, last, new GetFeedHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFeedTask);
    }
    private class GetFeedHandler extends Handler {
        private GetFeedObserver observer;

        public GetFeedHandler(GetFeedObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            observer.handleLoading();

            boolean success = msg.getData().getBoolean(GetFeedTask.SUCCESS_KEY);
            if (success) {
                List<Status> toAdd = (List<Status>) msg.getData().getSerializable(GetFeedTask.STATUSES_KEY);
                Boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);

                Status last = (toAdd.size() > 0) ? toAdd.get(toAdd.size() - 1) : null;

                observer.handleGetFeedSuccess(hasMorePages, last, toAdd);
            } else if (msg.getData().containsKey(GetFeedTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFeedTask.MESSAGE_KEY);
                observer.handleGetFeedFailure(message);
            } else if (msg.getData().containsKey(GetFeedTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFeedTask.EXCEPTION_KEY);
                observer.handleGetFeedThrewAnException(ex);
            }
        }
    }
}
