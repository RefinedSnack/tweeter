package edu.byu.cs.tweeter.client.presenter.paged;

import edu.byu.cs.tweeter.client.model.service.paged.FeedService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

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
    public void getPage(AuthToken authToken, User user, int pageSize, Status last)
    {
        new FeedService().getPage(authToken, user, pageSize, last, new PagedObserver<Status>());
    }


}
