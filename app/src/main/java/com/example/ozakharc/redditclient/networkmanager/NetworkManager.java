package com.example.ozakharc.redditclient.networkmanager;

public interface NetworkManager {

    void getDataFromReddit(String after, int limit);

    void setListener(NetworkManagerListener networkManagerListener);

    void cleanUp();

}

