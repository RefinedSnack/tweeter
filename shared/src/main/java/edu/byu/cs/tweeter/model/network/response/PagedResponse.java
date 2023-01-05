package edu.byu.cs.tweeter.model.network.response;

import java.util.ArrayList;
import java.util.List;

/**
 * A response that can indicate whether there is more data available from the server.
 */
public class PagedResponse <ITEM> extends Response
{

    private final boolean hasMorePages;
    private final List<ITEM> items;

    PagedResponse(boolean success, boolean hasMorePages, List<ITEM> items)
    {
        super(success);
        this.hasMorePages = hasMorePages;
        this.items = items;
    }

    PagedResponse(boolean success, String message, boolean hasMorePages)
    {
        super(success, message);
        this.hasMorePages = hasMorePages;
        items = new ArrayList<>();
    }

    /**
     * An indicator of whether more data is available from the server. A value of true indicates
     * that the result was limited by a maximum value in the request and an additional request
     * would return additional data.
     *
     * @return true if more data is available; otherwise, false.
     */
    public boolean getHasMorePages()
    {
        return hasMorePages;
    }

    public List<ITEM> getItems()
    {
        return items;
    }

}
