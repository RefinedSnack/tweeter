package edu.byu.cs.tweeter.model.network.response;

import java.io.Serializable;

/**
 * A base class for server responses.
 */
public abstract class Response implements Serializable
{

    private boolean success;
    private String message;

    protected Response()
    {
        this(true, null);
    }


    protected Response(String message)
    {
        this(false, message);
    }

    private Response(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }
}
