package com.example.ozakharc.redditclient.helpers;

import android.support.test.espresso.IdlingResource;
import android.util.Log;

import com.example.ozakharc.redditclient.MvpView;
import com.example.ozakharc.redditclient.ProgressListener;

public class ProgressIdlingResource implements IdlingResource{
    private static final String TAG = "ProgressIdlingResource";

    private IdlingResource.ResourceCallback resourceCallback;
    private MvpView mainActivity;

    public ProgressIdlingResource(MvpView activity){
        mainActivity = activity;

        Log.d(TAG, "ProgressIdlingResource: "+mainActivity.getClass().getSimpleName());
        ProgressListener progressListener = new ProgressListener() {
            @Override
            public void onProgressShown() {
            }

            @Override
            public void onProgressDismissed() {
                if (resourceCallback == null) {
                    return;
                }
                resourceCallback.onTransitionToIdle();
            }
        };
        mainActivity.setProgressListener (progressListener);
    }
    @Override
    public String getName() {
        return "Idling resource";
    }

    @Override
    public boolean isIdleNow() {
        return !mainActivity.isInProgress();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}
