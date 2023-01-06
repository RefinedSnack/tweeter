package edu.byu.cs.tweeter.model.network.response;

public class GetCountResponse extends Response
{
    private int count;

    public GetCountResponse(String message)
    {
        super(message);
    }

    public GetCountResponse(int count)
    {
        super();
        this.count = count;
    }

    public int getCount()
    {
        return count;
    }

}
