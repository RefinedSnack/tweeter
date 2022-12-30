package edu.byu.cs.tweeter.client.presenter.paged;

import edu.byu.cs.tweeter.client.model.service.paged.StoryService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status>
{
    public StoryPresenter(PagedView<Status> view)
    {
        super(view);
    }

    @Override
    protected String getPagedMessageInfix()
    {
        return "story";
    }

    @Override
    public void getPage(AuthToken authToken, User user, int pageSize)
    {
        new StoryService().getPage(authToken, user, pageSize, last, new PagedObserver());
    }
}
