package edu.byu.cs.tweeter.client.presenter.paged;

import edu.byu.cs.tweeter.client.model.service.paged.StoryService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

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
    public void getPage(AuthToken authToken, String targetUserAlias, int pageSize)
    {
        new StoryService().getPage(authToken, targetUserAlias, pageSize, last, new PagedObserver());
    }
}
