package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.response.PagedResponse;
import edu.byu.cs.tweeter.model.network.response.Response;

public abstract class PagedTask<T extends Serializable> extends AuthenticatedTask
{

    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";

    private final String targetUserAlias;
    private final int limit;
    private final T lastItem;
    private List<T> items;
    private boolean hasMorePages;

    protected PagedTask(AuthToken authToken, String targetUserAlias, int limit, T lastItem, Handler messageHandler)
    {
        super(authToken, messageHandler);
        this.targetUserAlias = targetUserAlias;
        this.limit = limit;
        this.lastItem = lastItem;
    }

    protected String getTargetUserAlias()
    {
        return targetUserAlias;
    }

    protected int getLimit()
    {
        return limit;
    }

    protected T getLastItem()
    {
        return lastItem;
    }

    @Override
    protected final Response runTask() throws IOException, TweeterRemoteException
    {
        PagedResponse<T> response = getItems();

        items = response.getItems();
        hasMorePages = response.hasMorePages();

        return response;
    }

    protected abstract PagedResponse<T> getItems() throws IOException, TweeterRemoteException;

    @Override
    protected final void loadSuccessBundle(Bundle msgBundle)
    {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }
}
