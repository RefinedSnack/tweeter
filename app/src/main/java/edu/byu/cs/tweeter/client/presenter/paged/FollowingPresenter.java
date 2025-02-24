package edu.byu.cs.tweeter.client.presenter.paged;

import edu.byu.cs.tweeter.client.model.service.paged.FollowingService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends PagedPresenter<User>
{
    public FollowingPresenter(PagedView<User> view)
    {
        super(view);
    }

    @Override
    protected String getPagedMessageInfix()
    {
        return "following";
    }

    @Override
    public void getPage(AuthToken authToken, String targetUserAlias, int pageSize)
    {
        new FollowingService().getPage(authToken, targetUserAlias, pageSize, last, new PagedObserver());
    }
}

