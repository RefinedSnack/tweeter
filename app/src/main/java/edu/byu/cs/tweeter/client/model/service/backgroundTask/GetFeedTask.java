package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.network.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.network.response.PagedResponse;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedTask<Status>
{

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler)
    {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected PagedResponse<Status> getItems() throws IOException, TweeterRemoteException
    {
        GetFeedRequest request = new GetFeedRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), getLastItem());
        GetFeedResponse response = (GetFeedResponse) getResponse(request, GetFeedRequest.class);
        return response;
    }
}
