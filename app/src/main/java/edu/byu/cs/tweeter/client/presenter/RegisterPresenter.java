package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter implements UserService.RegisterObserver
{
    public interface RegisterView
    {
        void displayErrorMessage(String message);
        void clearErrorMessage();

        void displayInfoMessage(String message);
        void clearInfoMessage();

        void navigateToUser(User user);
    }
    private RegisterView view;

    public RegisterPresenter(RegisterView view)
    {
        this.view = view;
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64)
    {
        String errMessage = validateRegistration(firstName, lastName, alias, password, imageBytesBase64);
        if (errMessage != null)
        {
            view.displayErrorMessage(errMessage);
            return;
        }
        view.displayInfoMessage("Registering...");
        new UserService().register(firstName,
                                   lastName,
                                   alias,
                                   password,
                                   imageBytesBase64,
                           this);
    }

    @Override
    public void handleRegisterSuccess(User user, AuthToken authToken)
    {
        view.clearInfoMessage();
        view.clearErrorMessage();

        view.displayInfoMessage("Hello " + user.getName());
        view.navigateToUser(user);
    }

    @Override
    public void handleRegisterFailure(String message)
    {
        view.displayInfoMessage("Failed to register: " + message);
    }

    @Override
    public void handleRegisterThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to register because of exception: " + ex.getMessage().toString());
    }


    private String validateRegistration(String firstName, String lastName, String alias, String password, String imageBytesBase64) {
        if (firstName.length() == 0) {
            return "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Last Name cannot be empty.";
        }
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }

        if (imageBytesBase64.length() == 0) {
            return "Profile image must be uploaded.";
        }
        return null;
    }

}
