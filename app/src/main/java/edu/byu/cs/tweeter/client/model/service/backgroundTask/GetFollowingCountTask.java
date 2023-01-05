package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.GetNumFollowingRequest;
import edu.byu.cs.tweeter.model.network.response.GetCountResponse;

/**
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends GetCountTask
{

    public GetFollowingCountTask(AuthToken authToken, User targetUser, Handler messageHandler)
    {
        super(authToken, targetUser, messageHandler);
    }

    @Override
    protected GetCountResponse runCountTask() throws IOException, TweeterRemoteException
    {
        GetNumFollowingRequest request = new GetNumFollowingRequest();
        response = getServerFacade().getNumFollowing(request);
        return response;
    }
}
