package com.example.ozakharc.redditclient;

import android.support.test.rule.ActivityTestRule;

import com.example.ozakharc.redditclient.detailed.DetailedActivity;

import org.junit.Rule;

public class DetailedActivityEspressoTests {
    @Rule
    public ActivityTestRule<DetailedActivity> activityTestRule = new ActivityTestRule<>(
            DetailedActivity.class);

}
