package edu.byu.cs.tweeter.client.presenter.paged;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.paged.PagedService;
import edu.byu.cs.tweeter.client.presenter.GetUserPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<ITEM> extends GetUserPresenter<PagedPresenter.PagedView>
{
    public interface PagedView<ITEM> extends GetUserView
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

    protected class PagedObserver<ITEM> extends InfixErrorObserver implements PagedService.GetPagedObserver<ITEM>
    {
        @Override
        public void handleSuccess(Boolean hasMorePages, ITEM last, List<ITEM> toAdd)
        {
            view.setHasMorePages(hasMorePages);
            view.setLast(last);
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
    public abstract void getPage(AuthToken authToken, User user, int pageSize, ITEM last);
}
