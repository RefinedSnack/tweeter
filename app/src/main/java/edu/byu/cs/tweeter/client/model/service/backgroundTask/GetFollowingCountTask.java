package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.GetFollowingCountRequest;
import edu.byu.cs.tweeter.model.network.response.GetCountResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowingCountResponse;

/**
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends GetCountTask
{

    public GetFollowingCountTask(AuthToken authToken, String targetUserAlias, Handler messageHandler)
    {
        super(authToken, targetUserAlias, messageHandler);
    }

    @Override
    protected GetCountResponse runCountTask() throws IOException, TweeterRemoteException
    {
        GetFollowingCountRequest request = new GetFollowingCountRequest(getAuthToken(), getTargetUserAlias());
        GetFollowingCountResponse response = (GetFollowingCountResponse) getResponse(request, GetFollowingCountRequest.class);
        return response;
    }
}
