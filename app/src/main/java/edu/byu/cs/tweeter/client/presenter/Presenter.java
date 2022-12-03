package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class Presenter<VIEW extends Presenter.View>
{
    public interface View
    {
        void displayInfoMessage(String message);
    }
    protected VIEW view;

    public Presenter(VIEW view)
    {
        this.view = view;
    }

    protected abstract class InfixErrorObserver implements ServiceObserver
    {
        @Override
        public void handleFailure(String message)
        {
            view.displayInfoMessage(String.format("Failed to %s: %s",
                    infixValue(),
                    message));
        }

        @Override
        public void handleException(Exception ex)
        {
            view.displayInfoMessage(String.format("Failed to %s because of exception: %s",
                    infixValue(),
                    ex.getMessage()));
        }

        protected abstract String infixValue();
    }
}
