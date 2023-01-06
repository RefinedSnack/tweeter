package edu.byu.cs.tweeter.model.network.request;

import java.io.Serializable;

public abstract class Request implements Serializable
{
    String message;

    protected Request()
    {
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
