package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.GetFollowersCountRequest;
import edu.byu.cs.tweeter.model.network.response.GetCountResponse;
import edu.byu.cs.tweeter.model.network.response.GetFollowersCountResponse;

/**
 * Background task that queries how many followers a user has.
 */
public class GetFollowersCountTask extends GetCountTask
{
    public GetFollowersCountTask(AuthToken authToken, String targetUser, Handler messageHandler)
    {
        super(authToken, targetUser, messageHandler);
    }

    @Override
    protected GetCountResponse runCountTask() throws IOException, TweeterRemoteException
    {
        GetFollowersCountRequest request = new GetFollowersCountRequest(getAuthToken(),getTargetUser());
        GetFollowersCountResponse response = (GetFollowersCountResponse) getResponse(request, GetFollowersCountRequest.class);
        return response;
    }
}
