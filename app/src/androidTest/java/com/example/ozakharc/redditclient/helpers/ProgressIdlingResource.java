package com.example.ozakharc.redditclient.helpers;

import android.support.test.espresso.IdlingResource;

import com.example.ozakharc.redditclient.MainActivity;

public class ProgressIdlingResource implements IdlingResource{

    private IdlingResource.ResourceCallback resourceCallback;
    private MainActivity mainActivity;

    public ProgressIdlingResource(MainActivity activity){
        mainActivity = activity;

        MainActivity.ProgressListener progressListener = new MainActivity.ProgressListener() {
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
