package edu.byu.cs.tweeter.client.presenter.paged;

import edu.byu.cs.tweeter.client.model.service.paged.FeedService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class FeedPresenter extends PagedPresenter<Status>
{
    public FeedPresenter(PagedView<Status> view)
    {
        super(view);
    }

    @Override
    protected String getPagedMessageInfix()
    {
        return "feed";
    }

    @Override
    public void getPage(AuthToken authToken, String targetUserAlias, int pageSize)
    {
        new FeedService().getPage(authToken, targetUserAlias, pageSize, last, new PagedObserver());
    }


}
