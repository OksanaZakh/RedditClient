package com.example.ozakharc.redditclient;

import android.support.annotation.VisibleForTesting;

@VisibleForTesting
public interface ProgressListener {

    void onProgressDismissed();

    void onProgressShown();
}
