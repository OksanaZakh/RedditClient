package com.example.ozakharc.redditclient;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.ozakharc.redditclient.helpers.ProgressIdlingResource;
import com.example.ozakharc.redditclient.helpers.SwipeUpHelper;
import com.example.ozakharc.redditclient.utils.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityToastTest {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setup() {
        idlingResource = new ProgressIdlingResource(activityTestRule.getActivity());
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregister() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }


    @Test
    public void showMessage_whenNoInternetConnection() {
        int numItemsLoaded=20;
        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition(numItemsLoaded-1));
        activityTestRule.getActivity().getConnection().setNotAvailable();
        onView(ViewMatchers.withId(R.id.rvList)).perform(SwipeUpHelper.swipeUp());
        onView(withText(Constants.NO_INTERNET_MESSAGE))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow()
                        .getDecorView())))).check(matches(isDisplayed()));
    }

}
