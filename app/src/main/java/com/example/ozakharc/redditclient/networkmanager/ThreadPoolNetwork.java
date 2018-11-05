package com.example.ozakharc.redditclient.networkmanager;

import com.example.ozakharc.redditclient.InternetConnection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolNetwork implements NetworkManager {

    private static final String TAG = "ThreadPoolNetwork";

    private NetworkManagerListener listener;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int MIN_POOL_SIZE = 1;
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private final BlockingQueue<Runnable> workQueue;
    private ThreadPoolExecutor executor;
    private InternetConnection connection;


    public ThreadPoolNetwork(InternetConnection connection) {
        this.connection = connection;
        workQueue = new LinkedBlockingQueue<>();
        executor = new ThreadPoolExecutor(
                MIN_POOL_SIZE,
                NUMBER_OF_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workQueue);
    }

    @Override
    public void getDataFromReddit(String after, int limit) {
        if (connection.isAvailable()) {
            executor.execute(new RedditTask(limit, after, listener));
        } else {
            listener.onNetworkIsUnavailable();
        }
    }

    @Override
    public void setListener(NetworkManagerListener networkManagerListener) {
        this.listener = networkManagerListener;
    }

    @Override
    public void cleanUp() {
        Runnable[] runnableArray = new Runnable[workQueue.size()];
        workQueue.toArray(runnableArray);
        int len = runnableArray.length;
        synchronized (this) {
            for (int runnableIndex = 0; runnableIndex < len; runnableIndex++) {
                Runnable runnable = runnableArray[runnableIndex];
                Thread thread = null;
                if (runnable instanceof RedditTask) {
                    thread = ((RedditTask) runnable).getCurrentThread();
                }
                if (null != thread) {
                    thread.interrupt();
                }
            }
        }
    }

    @Override
    public void getComments(String url) {

    }
}
