package com.example.ozakharc.redditclient.networkmanager;

import android.os.Handler;
import android.os.HandlerThread;

import com.example.ozakharc.redditclient.InternetConnection;
import com.example.ozakharc.redditclient.model.RepositoryManager;
import com.example.ozakharc.redditclient.utils.Constants;

public class HandleThreadNetwork implements NetworkManager {
    private static final String TAG = "HandleThreadNetwork";
    private HandlerThread thread;
    private Handler workerHandler;

    private InternetConnection connection;
    private NetworkManagerListener networkManagerListener;

    public HandleThreadNetwork(InternetConnection connection) {
        this.connection = connection;
        createWorkerThread();
    }

    public void createWorkerThread(){
        thread = new HandlerThread(Constants.REDDIT);
        thread.start();
        workerHandler = new Handler(thread.getLooper());
    }

    @Override
    public void getDataFromReddit(String after, int limit) {
        if (connection.isAvailable()) {
            if(!thread.isAlive()){
                createWorkerThread();
            }
            workerHandler.post(() -> new RedditTask(limit, after, networkManagerListener).run());

        } else networkManagerListener.onNetworkIsUnavailable();
    }

    @Override
    public void setListener(NetworkManagerListener networkManagerListener) {
        this.networkManagerListener = networkManagerListener;
    }

    @Override
    public void cleanUp() {
        thread.quit();
    }

    @Override
    public void getComments(String url) {

    }

    @Override
    public void setRepository(RepositoryManager repository) {

    }
}
