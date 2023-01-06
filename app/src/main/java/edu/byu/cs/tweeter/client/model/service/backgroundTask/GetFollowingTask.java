package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.network.response.GetFollowingResponse;
import edu.byu.cs.tweeter.model.network.response.PagedResponse;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedTask<User>
{

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler)
    {
        super(authToken, targetUser, limit, lastFollowee, messageHandler);
    }

    @Override
    protected PagedResponse<User> getItems() throws IOException, TweeterRemoteException
    {
        GetFollowingRequest request = new GetFollowingRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), getLastItem().getAlias());
        GetFollowingResponse response = (GetFollowingResponse) getResponse(request, GetFollowingRequest.class);
        return response;
    }
}
