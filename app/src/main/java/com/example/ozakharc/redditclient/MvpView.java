package com.example.ozakharc.redditclient;

import android.support.annotation.VisibleForTesting;

public interface MvpView {

    @VisibleForTesting
    void setProgressListener(ProgressListener progressListener);

    @VisibleForTesting
    boolean isInProgress();
}
