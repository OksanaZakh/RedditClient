package com.example.ozakharc.redditclient;

public interface NetworkManager {

    void getDataFromReddit(String after, int limit);

    void setListener(NetworkManagerListener networkManagerListener);

}

