package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class RegisterPresenter extends AuthenticationPresenter
{
    public RegisterPresenter(AuthenticationView view)
    {
        super(view);
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
                new AuthenticationObserver()
                {
                    @Override
                    protected String infixValue()
                    {
                        return "register";
                    }
                });
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
