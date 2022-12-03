package edu.byu.cs.tweeter.client.presenter.paged;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.paged.PagedService;
import edu.byu.cs.tweeter.client.presenter.Presenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<ITEM> extends Presenter<PagedPresenter.PagedView>
{
    public interface PagedView<ITEM> extends Presenter.view
    {
        void setLoading(boolean value);

        void removeLoadingFooter();

        void setLast(ITEM last);

        void addItems(List<ITEM> toAdd);

        void setHasMorePages(boolean value);

        void addLoadingFooter();
    }

    public PagedPresenter(PagedView<ITEM> view)
    {
        super(view);
    }

    protected class PagedObserver<ITEM> implements PagedService.GetPagedObserver<ITEM>
    {
        @Override
        public void handleSuccess(Boolean hasMorePages, ITEM last, List<ITEM> toAdd)
        {
            view.setHasMorePages(hasMorePages);
            view.setLast(last);
            view.addItems(toAdd);
        }

        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage(String.format("Failed to get %s: %s",
                    getMessagePrefix(), message));
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage(String.format("Failed to get %s because of exception: %s",
                    getMessagePrefix(), ex.getMessage()));
        }

        @Override
        public void handleLoading()
        {
            view.setLoading(false);
            view.removeLoadingFooter();
        }
    }

    protected abstract String getMessagePrefix();
    public abstract void getPage(AuthToken authToken, User user, int pageSize, ITEM last);
}
