package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryService
{
    public interface GetStoryObserver
    {
        void handleGetStorySuccess(Boolean user, Status pageSize, List<Status> last);
        void handleGetStoryFailure(String message);
        void handleGetStoryThrewAnException(Exception ex);
        void handleLoading();
    }

    public void getStory(AuthToken authToken, User user, int pageSize, Status last, GetStoryObserver observer)
    {
        GetStoryTask getStoryTask = new GetStoryTask(authToken,
                user, pageSize, last, new GetStoryHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getStoryTask);
    }
    private class GetStoryHandler extends Handler {
        private GetStoryObserver observer;

        public GetStoryHandler(GetStoryObserver observer)
        {
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            observer.handleLoading();

            boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
            if (success) {
                List<Status> toAdd = (List<Status>) msg.getData().getSerializable(GetStoryTask.STATUSES_KEY);
                Boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);

                Status last = (toAdd.size() > 0) ? toAdd.get(toAdd.size() - 1) : null;

                observer.handleGetStorySuccess(hasMorePages, last, toAdd);
            } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
                observer.handleGetStoryFailure(message);
            } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
                observer.handleGetStoryThrewAnException(ex);
            }
        }
    }
}
