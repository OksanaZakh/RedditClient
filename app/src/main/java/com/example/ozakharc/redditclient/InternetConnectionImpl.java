package com.example.ozakharc.redditclient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.VisibleForTesting;

import java.util.Objects;

public class InternetConnectionImpl implements InternetConnection {

    private Context context;
    private boolean checkAvailability = true;

    public InternetConnectionImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isAvailable() {
        if (checkAvailability) {
            return ((ConnectivityManager) Objects.requireNonNull(context.getSystemService
                    (Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo() != null;
        } else return false;
    }

    @VisibleForTesting
    @Override
    public void setNotAvailable() {
        checkAvailability = false;
    }
}
