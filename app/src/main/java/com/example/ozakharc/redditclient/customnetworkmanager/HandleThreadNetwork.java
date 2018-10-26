package com.example.ozakharc.redditclient.customnetworkmanager;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.example.ozakharc.redditclient.InternetConnection;
import com.example.ozakharc.redditclient.NetworkManager;
import com.example.ozakharc.redditclient.NetworkManagerListener;
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
    private Handler uiHandler;
    private InternetConnection connection;
    private NetworkManagerListener networkManagerListener;

    public HandleThreadNetwork(InternetConnection connection) {
        thread = new HandlerThread(Constants.REDDIT);
        thread.start();
        workerHandler = new Handler(thread.getLooper());
        uiHandler = new Handler(Looper.getMainLooper());
        this.connection = connection;
    }

    @Override
    public void getDataFromReddit(String after, int limit) {
        if (connection.isAvailable()) {
            workerHandler.post(() -> {
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
                    uiHandler.post(() -> networkManagerListener.onSuccessResponse(JsonParser.parse(result.toString())));
                } catch (Exception e) {
                    networkManagerListener.onResponseFailure();
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
            });
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
}
