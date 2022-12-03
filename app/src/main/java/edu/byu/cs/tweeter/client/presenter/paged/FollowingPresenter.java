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
    protected String getMessagePrefix()
    {
        return "following";
    }

    @Override
    public void getPage(AuthToken authToken, User user, int pageSize, User last)
    {
        new FollowingService().getPage(authToken, user, pageSize, last, new PagedObserver<User>());
    }
}

