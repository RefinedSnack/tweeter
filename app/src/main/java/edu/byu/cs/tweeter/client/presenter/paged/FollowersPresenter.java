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
    public void getPage(AuthToken authToken, User user, int pageSize, User last)
    {
        new FollowersService().getPage(authToken, user, pageSize, last, new PagedObserver<User>());
    }
}


