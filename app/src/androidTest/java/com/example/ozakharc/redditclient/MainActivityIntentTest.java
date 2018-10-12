package com.example.ozakharc.redditclient;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.detailed.DetailedActivity;
import com.example.ozakharc.redditclient.helpers.ProgressIdlingResource;
import com.example.ozakharc.redditclient.utils.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityIntentTest {
    private IdlingResource idlingResource;
    @Rule
    public IntentsTestRule<MainActivity> rule = new IntentsTestRule<>(
            MainActivity.class);

    @Before
    public void setup() {
        idlingResource = new ProgressIdlingResource(rule.getActivity());
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregister() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void checkIntent_forLaunchingDetailedActivity() {
        int targetPosition=7;
        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition(targetPosition)).perform(click());
        NewsItem targetItem=rule.getActivity().getAllNewsItems().get(targetPosition-1);
        intended(allOf(hasComponent(DetailedActivity.class.getName()), hasExtra(Constants.NEWS_ITEM, targetItem)));
    }
}
