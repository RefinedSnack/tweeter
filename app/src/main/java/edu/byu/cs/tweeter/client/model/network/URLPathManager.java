package edu.byu.cs.tweeter.client.model.network;

public interface URLPathManager
{
   String getServerURL();
   String getLoginPath();
   String getRegisterPath();
   String getFollowingPath();
}
