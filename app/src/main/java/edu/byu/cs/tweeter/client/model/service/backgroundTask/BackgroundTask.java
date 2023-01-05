package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.network.ConstantURLPathManager;
import edu.byu.cs.tweeter.client.model.network.ServerFacade;
import edu.byu.cs.tweeter.client.model.network.URLPathManager;
import edu.byu.cs.tweeter.model.network.TweeterRemoteException;
import edu.byu.cs.tweeter.model.network.request.Request;
import edu.byu.cs.tweeter.model.network.response.Response;
import edu.byu.cs.tweeter.util.FakeData;

public abstract class BackgroundTask implements Runnable
{
    protected static final String LOG_TAG = "BackgroundTask";

    public static final String SUCCESS_KEY = "success";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";

    /**
     * Message handler that will receive task results.
     */
    private final Handler messageHandler;

    private ServerFacade serverFacade;

    protected BackgroundTask(Handler messageHandler)
    {
        this.messageHandler = messageHandler;
    }

    @Override
    public void run()
    {
        try
        {
            Response response = runTask();
            handleResponse(response);
        } catch (IOException | TweeterRemoteException e)
        {
            Log.e(LOG_TAG, e.getMessage());
            sendExceptionMessage(e);
        }
    }

    protected void handleResponse(Response response)
    {
        if (response.isSuccess())
            sendSuccessMessage();
        else
            sendFailedMessage(response.getMessage());
    }

    protected abstract Response runTask() throws IOException, TweeterRemoteException;

    protected FakeData getFakeData()
    {
        return FakeData.getInstance();
    }

    public void sendSuccessMessage()
    {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);
        loadSuccessBundle(msgBundle);
        sendMessage(msgBundle);
    }

    public void sendFailedMessage(String errorMessage)
    {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        msgBundle.putString(MESSAGE_KEY, errorMessage);
        sendMessage(msgBundle);
    }

    public void sendExceptionMessage(Exception exception)
    {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        msgBundle.putSerializable(EXCEPTION_KEY, exception);
        sendMessage(msgBundle);
    }

    /**
     * Add additional information during a successful task to a Bundle
     *
     * @param msgBundle The bundle send to the handler with the results of the task
     */
    protected void loadSuccessBundle(Bundle msgBundle)
    {
        // By default, do nothing
        // this should be overridden to add needed data to the bundle
    }

    private void sendMessage(Bundle msgBundle)
    {
        Message msg = Message.obtain();
        msg.setData(msgBundle);
        messageHandler.sendMessage(msg);
    }

    private ServerFacade getServerFacade()
    {
        if (serverFacade == null)
        {
            serverFacade = new ServerFacade(URLPathManagerFactory());
        }

        return serverFacade;
    }

    public URLPathManager URLPathManagerFactory()
    {
        return new ConstantURLPathManager();
    }

    protected Response getResponse(Request request, Class<?> type) throws IOException, TweeterRemoteException
    {
        return getServerFacade().run(request, type);
    }
}
