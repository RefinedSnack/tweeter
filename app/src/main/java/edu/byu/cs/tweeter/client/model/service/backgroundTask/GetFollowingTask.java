package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.FollowingRequest;
import edu.byu.cs.tweeter.model.network.response.FollowingResponse;
import edu.byu.cs.tweeter.model.network.response.PagedResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask
{

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler)
    {
        super(authToken, targetUser, limit, lastFollowee, messageHandler);
    }

    @Override
    protected PagedResponse<User> getItems() throws IOException, TweeterRemoteException
    {
        FollowingRequest request = new FollowingRequest(
                getAuthToken(),
                getTargetUser().getAlias(),
                getLimit(),
                getLastItem().getAlias());
        FollowingResponse response = getServerFacade().getFollowing(request);
        return new Pair<>(response.getFollowees(), response.isSuccess());
    }
}
