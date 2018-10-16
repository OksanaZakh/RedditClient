package com.example.ozakharc.redditclient;

import android.support.annotation.VisibleForTesting;

public interface InternetConnection {
    boolean isAvailable();

    @VisibleForTesting
    void setNotAvailable();
}
