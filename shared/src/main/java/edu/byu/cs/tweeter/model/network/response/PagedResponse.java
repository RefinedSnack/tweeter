package edu.byu.cs.tweeter.model.network.response;

import java.util.List;

/**
 * A response that can indicate whether there is more data available from the server.
 */
public abstract class PagedResponse <ITEM> extends Response
{

    private final boolean hasMorePages;
    private final List<ITEM> items;

    protected PagedResponse(String message)
    {
        super(message);
        this.hasMorePages = false;
        items = null;
    }

    protected PagedResponse(boolean hasMorePages, List<ITEM> items)
    {
        super();
        this.hasMorePages = hasMorePages;
        this.items = items;
    }

    public boolean hasMorePages()
    {
        return hasMorePages;
    }

    public List<ITEM> getItems()
    {
        return items;
    }

}
