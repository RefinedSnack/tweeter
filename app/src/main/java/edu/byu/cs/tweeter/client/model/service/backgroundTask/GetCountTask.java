package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.response.GetCountResponse;
import edu.byu.cs.tweeter.model.network.response.Response;

public abstract class GetCountTask extends AuthenticatedTask {

    public static final String COUNT_KEY = "count";

    /**
     * The user whose count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private final String targetUserAlias;

    private int count;

    protected GetCountTask(AuthToken authToken, String targetUserAlias, Handler messageHandler) {
        super(authToken, messageHandler);
        this.targetUserAlias = targetUserAlias;
    }

    protected String getTargetUserAlias() {
        return targetUserAlias;
    }

    @Override
    protected Response runTask() throws IOException, TweeterRemoteException
    {
        GetCountResponse response = runCountTask();
        count = response.getCount();
        return response;
    }

    protected abstract GetCountResponse runCountTask() throws IOException, TweeterRemoteException;

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putInt(COUNT_KEY, count);
    }
}
