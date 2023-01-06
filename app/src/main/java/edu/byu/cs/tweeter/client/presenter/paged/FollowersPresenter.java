package edu.byu.cs.tweeter.client.presenter.paged;

import edu.byu.cs.tweeter.client.model.service.paged.FollowersService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedPresenter<User>
{
    public FollowersPresenter(PagedView<User> view)
    {
        super(view);
    }

    @Override
    protected String getPagedMessageInfix()
    {
        return "followers";
    }

    @Override
    public void getPage(AuthToken authToken, String targetUserAlias, int pageSize)
    {
        new FollowersService().getPage(authToken, targetUserAlias, pageSize, last, new PagedObserver());
    }
}


