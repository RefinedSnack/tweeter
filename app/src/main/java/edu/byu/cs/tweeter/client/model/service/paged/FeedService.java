package edu.byu.cs.tweeter.client.model.service.paged;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class FeedService extends PagedService<Status>
{
    @Override
    protected PagedTask getPageTask(AuthToken authToken, String targetUserAlias, int pageSize, Status last, GetPagedObserver<Status> observer)
    {
        return new GetFeedTask(authToken, targetUserAlias, pageSize, last, new GetPagedHandler<Status>(observer));
    }
}
