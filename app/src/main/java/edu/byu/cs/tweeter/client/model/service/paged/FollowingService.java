package edu.byu.cs.tweeter.client.model.service.paged;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingService extends PagedService<User>
{
    @Override
    protected PagedTask getPageTask(AuthToken authToken, String targetUserAlias, int pageSize, User last, GetPagedObserver<User> observer)
    {
        return new GetFollowingTask(authToken, targetUserAlias, pageSize, last, new GetPagedHandler<>(observer));
    }
}
