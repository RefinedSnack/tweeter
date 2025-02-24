package edu.byu.cs.tweeter.model.network.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterResponse extends AuthenticateResponse
{
    public RegisterResponse(String message)
    {
        super(message);
    }

    public RegisterResponse(User user, AuthToken authToken)
    {
        super(user, authToken);
    }
}
