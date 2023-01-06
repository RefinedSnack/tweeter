package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.network.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.network.response.PagedResponse;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedTask<User>
{

    public GetFollowersTask(AuthToken authToken, String targetUserAlias, int limit, User lastFollower,
                            Handler messageHandler)
    {
        super(authToken, targetUserAlias, limit, lastFollower, messageHandler);
    }

    @Override
    protected PagedResponse<User> getItems() throws IOException, TweeterRemoteException
    {
        GetFollowersRequest request = new GetFollowersRequest(getAuthToken(), getTargetUserAlias(), getLimit(), getLastItem().getAlias());
        GetFollowersResponse response = (GetFollowersResponse) getResponse(request, GetFollowersRequest.class);
        return response;
    }
}
