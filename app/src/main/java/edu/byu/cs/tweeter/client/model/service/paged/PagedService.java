package edu.byu.cs.tweeter.client.model.service.paged;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class PagedService<ITEM>
{
    public interface GetPagedObserver <ITEM> extends ServiceObserver
    {
        void handleSuccess(Boolean hasMorePages, ITEM last, List<ITEM> toAdd);
        void handleLoading();
    }

    public void getPage(AuthToken authToken, String targetUserAlias, int pageSize, ITEM last, GetPagedObserver<ITEM> observer)
    {
        BackgroundTaskUtils.runTask(getPageTask(authToken, targetUserAlias, pageSize, last, observer));
    }

    abstract protected PagedTask getPageTask(AuthToken authToken, String targetUserAlias, int pageSize, ITEM last, GetPagedObserver<ITEM> observer);

    protected class GetPagedHandler<PAGE_ITEM> extends BackgroundTaskHandler<GetPagedObserver<PAGE_ITEM>>
    {
        public GetPagedHandler(GetPagedObserver<PAGE_ITEM> observer)
        {
            super(observer);
        }

        @Override
        protected void handleSuccessMessage(GetPagedObserver<PAGE_ITEM> observer, Bundle data)
        {
            observer.handleLoading();

            List<PAGE_ITEM> toAdd = (List<PAGE_ITEM>) data.getSerializable(PagedTask.ITEMS_KEY);
            Boolean hasMorePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);

            PAGE_ITEM last = (toAdd.size() > 0) ? toAdd.get(toAdd.size() - 1) : null;

            observer.handleSuccess(hasMorePages, last, toAdd);
        }
    }
}
