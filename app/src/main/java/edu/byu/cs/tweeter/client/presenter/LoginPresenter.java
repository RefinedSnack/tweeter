package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class LoginPresenter extends AuthenticationPresenter
{
    public LoginPresenter(AuthenticationView view)
    {
        super(view);
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
        new UserService().login(alias, password, new AuthenticationObserver()
        {
            @Override
            protected String infixValue()
            {
                return "login";
            }
        });
    }

    private String validateLogin(String alias, String password)
    {
        if (alias.charAt(0) != '@')
        {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2)
        {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0)
        {
            return "Password cannot be empty.";
        }
        return null;
    }
}
