package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.RegisterRequest;
import edu.byu.cs.tweeter.model.network.response.AuthenticateResponse;
import edu.byu.cs.tweeter.model.network.response.RegisterResponse;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticateTask
{

    /**
     * The user's first name.
     */
    private final String firstName;

    /**
     * The user's last name.
     */
    private final String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private final String image;

    public RegisterTask(String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler)
    {
        super(messageHandler, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    @Override
    protected AuthenticateResponse runAuthenticationTask() throws IOException, TweeterRemoteException
    {
        RegisterRequest request = new RegisterRequest(getFirstName(), getLastName(), getUsername(), getPassword(), getImage());
        RegisterResponse response = (RegisterResponse) getResponse(request, RegisterRequest.class);
        return response;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getImage()
    {
        return image;
    }
}
