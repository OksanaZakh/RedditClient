package com.example.ozakharc.redditclient;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.detailed.DetailedActivity;
import com.example.ozakharc.redditclient.helpers.DrawableMatcher;
import com.example.ozakharc.redditclient.helpers.NewsItemCreator;
import com.example.ozakharc.redditclient.utils.Constants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.ozakharc.redditclient.helpers.NewsItemCreator.AUTHOR;
import static com.example.ozakharc.redditclient.helpers.NewsItemCreator.SELF_TEXT;
import static com.example.ozakharc.redditclient.helpers.NewsItemCreator.TITLE;
import static com.example.ozakharc.redditclient.helpers.NewsItemCreator.URL;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class DetailedActivityEspressoTest {

    @Rule
    public ActivityTestRule<DetailedActivity> activityTestRule = new ActivityTestRule<>(
            DetailedActivity.class, false, false);


    @Before
    public void setup() {
        NewsItem newsItem = NewsItemCreator.createNewsItem();
        Intent intent = new Intent();
        intent.putExtra(Constants.NEWS_ITEM, newsItem);
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void appNameDisplayed_inToolBar() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(allOf(withParent(withId(R.id.toolbar)), isDisplayed())));
    }

    @Test
    public void backButtonIsVisible_andHasProperDrawable() {
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(allOf(DrawableMatcher.withDrawable(R.drawable.ic_left_arrow), isDisplayed()))));
    }

    @Test
    public void checkDataVisibility_andText() {
        onView(withId(R.id.tvAuthor)).check(matches(allOf(isDisplayed(), withText(AUTHOR))));
        onView(withId(R.id.tvTitle)).check(matches(allOf(isDisplayed(), withText(TITLE))));
        onView(withId(R.id.tvDate)).check(matches(isDisplayed()));
        onView(withId(R.id.ivPhoto)).check(matches(isDisplayed()));
        onView(withId(R.id.tvLink)).check(matches(allOf(isDisplayed(), withText(URL))));
        onView(withId(R.id.tvDescription)).check(matches(allOf(isDisplayed(), withText(SELF_TEXT))));
    }

//    @Test
//    public void imageDialogVisibility() {
//        onView(withId(R.id.ivPhoto)).perform(click());
//        try {
//            wait(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //onView(DrawableMatcher.withDrawable(R.drawable.ic_left_arrow)).inRoot(isDialog()).check(matches(isDisplayed()));
//        onView(hasDescendant(isDisplayed())).inRoot(isDialog());
////        onView(hasDescendant(isDisplayed())).inRoot(not(isDialog()));
//    }


}
