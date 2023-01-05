package edu.byu.cs.tweeter.client.model.network;

public class ConstantURLPathManager implements URLPathManager
{

    @Override
    public String getServerURL()
    {
        return "SERVER_URL";
    }

    @Override
    public String getLoginPath()
    {
        return "/login";
    }

    @Override
    public String getRegisterPath()
    {
        return "/register";
    }

    @Override
    public String getFollowingPath()
    {
        return "/get/following";
    }
}
