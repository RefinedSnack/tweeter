package edu.byu.cs.tweeter.client.model.service.paged;

import android.os.Bundle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedService<ITEM>
{
    public interface GetPagedObserver <ITEM> extends ServiceObserver
    {
        void handleSuccess(Boolean hasMorePages, ITEM last, List<ITEM> toAdd);
        void handleLoading();
    }

    public void getPage(AuthToken authToken, User user, int pageSize, ITEM last, GetPagedObserver<ITEM> observer)
    {
        PagedTask pagedTask = getPageTask(authToken, user, pageSize, last, observer);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(pagedTask);
    }

    abstract protected PagedTask getPageTask(AuthToken authToken, User user, int pageSize, ITEM last, GetPagedObserver<ITEM> observer);

    protected class GetPagedHandler<ITEM> extends BackgroundTaskHandler<GetPagedObserver<ITEM>>
    {
        public GetPagedHandler(GetPagedObserver<ITEM> observer)
        {
            super(observer);
        }

        @Override
        protected void handleSuccessMessage(GetPagedObserver<ITEM> observer, Bundle data)
        {
            observer.handleLoading();

            List<ITEM> toAdd = (List<ITEM>) data.getSerializable(PagedTask.ITEMS_KEY);
            Boolean hasMorePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);

            ITEM last = (toAdd.size() > 0) ? toAdd.get(toAdd.size() - 1) : null;

            observer.handleSuccess(hasMorePages, last, toAdd);
        }
    }
}
