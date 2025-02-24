package edu.byu.cs.tweeter.client.presenter.paged;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.paged.PagedService;
import edu.byu.cs.tweeter.client.presenter.NavigateToUserPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class PagedPresenter<ITEM> extends NavigateToUserPresenter<PagedPresenter.PagedView>
{
    public interface PagedView<ITEM> extends NavigateToUserView
    {
        void setLoading(boolean value);

        void addItems(List<ITEM> toAdd);

        void setHasMorePages(boolean value);

        void removeLoadingFooter();
    }

    protected ITEM last;
    public PagedPresenter(PagedView<ITEM> view)
    {
        super(view);
    }

    protected class PagedObserver extends InfixErrorObserver implements PagedService.GetPagedObserver<ITEM>
    {
        @Override
        public void handleSuccess(Boolean hasMorePages, ITEM lastItem, List<ITEM> toAdd)
        {
            view.setHasMorePages(hasMorePages);
            last = lastItem;
            view.addItems(toAdd);
        }

        @Override
        protected String infixValue()
        {
            return getPagedMessageInfix();
        }

        @Override
        public void handleLoading()
        {
            view.setLoading(false);
            view.removeLoadingFooter();
        }
    }

    protected abstract String getPagedMessageInfix();
    public abstract void getPage(AuthToken authToken, String targetUserAlias, int pageSize);
}
