package edu.byu.cs.tweeter.client.model.service.paged;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryService extends PagedService<Status>
{
    @Override
    protected PagedTask getPageTask(AuthToken authToken, User user, int pageSize, Status last, GetPagedObserver<Status> observer)
    {
        return new GetStoryTask(authToken, user, pageSize, last, new GetPagedHandler<>(observer));
    }
}
