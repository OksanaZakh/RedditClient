package com.example.ozakharc.redditclient;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Objects;

public class InternetConnectionImpl implements InternetConnection {

    private Context context;

    public InternetConnectionImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isAvailable() {
        return ((ConnectivityManager) Objects.requireNonNull(context.getSystemService
                (Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo() != null;
    }
}
