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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class DetailedIntentTest {


    private IdlingResource idlingResource;
    private IntentsTestRule<MainActivity> mainIntentRule = new IntentsTestRule<>(
            MainActivity.class);
    private IntentsTestRule<DetailedActivity> detailedIntentRule = new IntentsTestRule<>(
            DetailedActivity.class, false, false);


    @Rule
    public RuleChain chain = RuleChain.outerRule(mainIntentRule).around(detailedIntentRule);


    @Before
    public void setup() {
        idlingResource = new ProgressIdlingResource(mainIntentRule.getActivity());
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregister() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void goToWebPage_whenLinkClicked() {
        int targetPosition=1;
        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition(targetPosition+1));
        NewsItem item=mainIntentRule.getActivity().getAllNewsItems().get(targetPosition-1);
        while(item.getUrl().isEmpty()){
            targetPosition++;
            item=mainIntentRule.getActivity().getAllNewsItems().get(targetPosition-1);
        }
        onView(withId(R.id.rvList)).perform(RecyclerViewActions.actionOnItemAtPosition(targetPosition, click()));
        onView(withId(R.id.tvLink)).perform(click());
        intended(hasAction(Intent.ACTION_VIEW));
    }
}
