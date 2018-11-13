package com.example.ozakharc.redditclient.networkmanager;

import com.example.ozakharc.redditclient.model.RepositoryManager;

public interface NetworkManager {

    void getDataFromReddit(String after, int limit);

    void setListener(NetworkManagerListener networkManagerListener);

    void cleanUp();

    void getComments(String url);

    void setRepository(RepositoryManager repository);

}

