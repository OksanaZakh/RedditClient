package com.example.ozakharc.redditclient;

import android.app.Activity;
import android.content.Context;

import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.detailed.DetailedActivity;
import com.example.ozakharc.redditclient.helpers.ActivityIdentification;
import com.example.ozakharc.redditclient.helpers.ProgressIdlingResource;
import com.example.ozakharc.redditclient.utils.DateConverter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private static final String TAG = "MainActivityEspressoTes";
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
    public void scrollToItemBelowFold_checkItsText_andVisibility() {
        int targetItemPosition = 18;
        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition(targetItemPosition));
        NewsItem item = getNewsItemsLoaded().get(targetItemPosition);

        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(targetItemPosition, hasDescendant(allOf(withText(R.string.posted_by), isDisplayed())))));
        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(targetItemPosition, hasDescendant(allOf(withText(R.string.date), isDisplayed())))));
        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(targetItemPosition, hasDescendant(allOf(withText(R.string.comments), isDisplayed())))));

        if (URLUtil.isValidUrl(item.getThumbnail())){
            onView(ViewMatchers.withId(R.id.rvList))
                    .check(matches(atPosition(targetItemPosition, hasDescendant(allOf(withId(R.id.ivThumbnail), isDisplayed())))));
        }else{
            onView(ViewMatchers.withId(R.id.rvList))
                    .check(matches(atPosition(targetItemPosition, hasDescendant(allOf(withId(R.id.ivThumbnail), not(isDisplayed()))))));
        }

        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(targetItemPosition, hasDescendant(allOf(withId(R.id.tvDate),
                        withText(DateConverter.getStringDate(item.getCreatedUtc())), isDisplayed())))));
        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(targetItemPosition, hasDescendant(allOf(withId(R.id.tvNumComments)
                        , withText(item.getNumComments().toString()), isDisplayed())))));
        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(targetItemPosition, hasDescendant(allOf(withId(R.id.tvAuthor), withText(item.getAuthor()), isDisplayed())))));

        assertTrue(getNewsItemsLoaded().size() > 0);
    }

    @Test
    public void navigateToDetailedActivity_whenClickOnItem() {
        onView(ViewMatchers.withId(R.id.rvList)).perform(click());
        Activity activity = ActivityIdentification.getActivityInstance();
        boolean isNavigateToDetailed = (activity instanceof DetailedActivity);
        assertTrue(isNavigateToDetailed);
    }


    @Test
    public void appNameDisplayed_inActionBar() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
    }

//    @Ignore
//    public void showMessage_whenNoInternetConnection() {
//        disconnect();
//        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition(19));
//        onView(withId(R.id.rvList)).perform(swipeUp());
//        onView(withText(Constants.NO_INTERNET_MESSAGE)).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
//    }

    @Test
    public void loadDifferentItems_withPagination_checkItsCount() {
        int numItemsLoadedFirst=20;
        int numItemsLoadedSecondTime=40;
        int numItemsLoadedThirdTime=60;

        //First call for loading
        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition(numItemsLoadedFirst-1));
        assertEquals(numItemsLoadedFirst, getNewsItemsLoaded().size());
        onView(ViewMatchers.withId(R.id.rvList)).perform(swipeUp());

        //Second call for loading
        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition(numItemsLoadedSecondTime-1));
        assertEquals(numItemsLoadedSecondTime, getNewsItemsLoaded().size());
        onView(ViewMatchers.withId(R.id.rvList)).perform(swipeUp());

        //Third call for loading
        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition(numItemsLoadedThirdTime-1));
        assertEquals(numItemsLoadedThirdTime, getNewsItemsLoaded().size());

        //Check whether loaded items are unique
        NewsItem newsItemLastPositionFirstLoad=getNewsItemsLoaded().get(numItemsLoadedFirst-1);
        NewsItem newsItemLastPositionSecondLoad=getNewsItemsLoaded().get(numItemsLoadedSecondTime-1);
        NewsItem newsItemLastPositionThirdLoad=getNewsItemsLoaded().get(numItemsLoadedThirdTime-1);

        Assert.assertNotEquals(newsItemLastPositionFirstLoad, newsItemLastPositionSecondLoad);
        Assert.assertNotEquals(newsItemLastPositionThirdLoad, newsItemLastPositionSecondLoad);
        Assert.assertNotEquals(newsItemLastPositionFirstLoad, newsItemLastPositionThirdLoad);
    }


    public List<NewsItem> getNewsItemsLoaded() {
        Log.d(TAG, "getNewsItemsLoaded: " + activityTestRule.getActivity().getAllNewsItems().size());
        return activityTestRule.getActivity().getAllNewsItems();
    }

    public void disconnect() {
        WifiManager wifiManager = (WifiManager) activityTestRule.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }


    public static ViewAction swipeUp() {
        return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.BOTTOM_CENTER,
                GeneralLocation.TOP_CENTER, Press.FINGER);
    }

}
