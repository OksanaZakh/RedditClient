package com.example.ozakharc.redditclient;

public interface NetworkManager {

    void getDataFromReggit(NetworkManagerListener networkManagerListener);

    void setAfter(String after);

    void setLimit(int limit);

}

