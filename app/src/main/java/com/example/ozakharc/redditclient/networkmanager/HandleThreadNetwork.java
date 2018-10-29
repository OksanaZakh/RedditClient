package com.example.ozakharc.redditclient.networkmanager;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.example.ozakharc.redditclient.InternetConnection;
import com.example.ozakharc.redditclient.utils.Constants;
import com.example.ozakharc.redditclient.utils.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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


    class RedditTask implements Runnable {

        private int limit;
        private String after;
        private NetworkManagerListener listener;
        private Handler uiHandler;

        public RedditTask(int limit, String after, NetworkManagerListener listener) {
            this.limit = limit;
            this.after = after;
            this.listener = listener;
            uiHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(String.format(Constants.URL_HANDLER_THREAD, limit, after));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(Constants.GET_METHOD);
                connection.connect();
                StringBuilder result = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    result.append(line).append("\n");
                }
                uiHandler.post(() -> listener.onSuccessResponse(JsonParser.parse(result.toString())));
            } catch (Exception e) {
                listener.onResponseFailure();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage());
                    }
                }
            }
        }
    }
}
