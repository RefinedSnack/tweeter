package edu.byu.cs.tweeter.model.network.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class TargetedUserRequest extends AuthenticatedRequest
{
    private String targetAlias;

    protected TargetedUserRequest()
    {
    }

    public TargetedUserRequest(AuthToken authToken, String targetAlias)
    {
        super(authToken);
        this.targetAlias = targetAlias;
    }

    public String getTargetAlias()
    {
        return targetAlias;
    }

    public void setTargetAlias(String targetAlias)
    {
        this.targetAlias = targetAlias;
    }
}
