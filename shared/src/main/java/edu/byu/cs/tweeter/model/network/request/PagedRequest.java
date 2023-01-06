package edu.byu.cs.tweeter.model.network.request;

import java.io.Serializable;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class PagedRequest<ITEM extends Serializable> extends TargetedUserRequest
{
    int limit;
    ITEM last;

    protected PagedRequest() {}

    public PagedRequest(AuthToken authToken, String target, int limit, ITEM last)
    {
        super(authToken, target);
        this.limit = limit;
        this.last = last;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public ITEM getLast()
    {
        return last;
    }

    public void setLast(ITEM last)
    {
        this.last = last;
    }
}
