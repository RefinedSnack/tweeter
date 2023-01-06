package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.network.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.network.response.PagedResponse;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedTask<Status>
{

    public GetStoryTask(AuthToken authToken, String targetUserAlias, int limit, Status lastStatus,
                        Handler messageHandler)
    {
        super(authToken, targetUserAlias, limit, lastStatus, messageHandler);
    }

    @Override
    protected PagedResponse<Status> getItems() throws IOException, TweeterRemoteException
    {
        GetStoryRequest request = new GetStoryRequest(getAuthToken(), getTargetUserAlias(), getLimit(), getLastItem());
        GetStoryResponse response = (GetStoryResponse) getResponse(request, GetStoryRequest.class);
        return response;
    }
}
