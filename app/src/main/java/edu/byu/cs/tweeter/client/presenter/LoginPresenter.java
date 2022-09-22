package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter implements UserService.LoginObserver
{
    public interface LoginView
    {
        void displayErrorMessage(String message);
        void clearErrorMessage();

        void displayInfoMessage(String message);
        void clearInfoMessage();

        void navigateToUser(User user);
    }
    private LoginView view;

    public LoginPresenter(LoginView view)
    {
        this.view = view;
    }

    public void login(String alias, String password)
    {
        String errMessage = validateLogin(alias, password);
        if (errMessage != null)
        {
            view.displayErrorMessage(errMessage);
            return;
        }
        view.displayInfoMessage("Logging in...");
        new UserService().login(alias, password, this);
    }

    @Override
    public void handleLoginSuccess(User user, AuthToken authToken)
    {
        view.clearInfoMessage();
        view.clearErrorMessage();

        view.displayInfoMessage("Hello " + user.getName());
        view.navigateToUser(user);
    }

    @Override
    public void handleLoginFailure(String message)
    {
        view.displayInfoMessage("Failed to login: " + message);
    }

    @Override
    public void handleLoginThrewAnException(Exception ex)
    {
        view.displayInfoMessage("Failed to login because of exception: " + ex.getMessage().toString());
    }


    private String validateLogin(String alias, String password) {
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }
        return null;
    }

}
