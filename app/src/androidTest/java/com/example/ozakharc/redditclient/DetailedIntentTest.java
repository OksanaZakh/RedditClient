package com.example.ozakharc.redditclient;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.detailed.DetailedActivity;
import com.example.ozakharc.redditclient.helpers.NewsItemCreator;
import com.example.ozakharc.redditclient.utils.Constants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.ozakharc.redditclient.helpers.NewsItemCreator.URL;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class DetailedIntentTest {

    @Rule
    public IntentsTestRule<DetailedActivity> rule = new IntentsTestRule<>(
            DetailedActivity.class, false, false);

    @Before
    public void setup() {
        NewsItem newsItem = NewsItemCreator.createNewsItem();
        Intent intent = new Intent();
        intent.putExtra(Constants.NEWS_ITEM, newsItem);
        rule.launchActivity(intent);
    }

    @Test
    public void goToWebPage_whenLinkClicked() {
        onView(withId(R.id.tvLink)).perform(click());
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData(URL)));
    }
}
