package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.GetNumFollowersRequest;
import edu.byu.cs.tweeter.model.network.response.GetCountResponse;
import edu.byu.cs.tweeter.model.network.response.GetNumFollowersResponse;

/**
 * Background task that queries how many followers a user has.
 */
public class GetFollowersCountTask extends GetCountTask
{

    public GetFollowersCountTask(AuthToken authToken, User targetUser, Handler messageHandler)
    {
        super(authToken, targetUser, messageHandler);
    }

    @Override
    protected GetCountResponse runCountTask() throws IOException, TweeterRemoteException
    {
        GetNumFollowersRequest request = new GetNumFollowersRequest();
        GetNumFollowersResponse response = (GetNumFollowersResponse) getResponse(request, GetNumFollowersRequest.class);
        return response;
    }
}
