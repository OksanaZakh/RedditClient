package com.example.ozakharc.redditclient;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ozakharc.redditclient.detailed.DetailedActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {
    private IdlingResource idlingResource;
    private static final int TARGET_POSITION = 18;

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

        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition(TARGET_POSITION))
                .check(matches(atPosition(TARGET_POSITION, hasDescendant(withText("Posted by:")))));
        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(TARGET_POSITION, hasDescendant(withText("Date:")))));
        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(TARGET_POSITION, hasDescendant(withText("Comments")))));
        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(TARGET_POSITION, hasDescendant(withId(R.id.tvAuthor)))));
        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(TARGET_POSITION, hasDescendant(withId(R.id.tvDate)))));
        onView(ViewMatchers.withId(R.id.rvList))
                .check(matches(atPosition(TARGET_POSITION, hasDescendant(withId(R.id.tvNumComments)))));

        onView(ViewMatchers.withId(R.id.rvList)).check(matches(atPosition(TARGET_POSITION, isDisplayed())));
    }

    @Test
    public void navigateToDetailedActivity_whenClickOnItem() {
        onView(ViewMatchers.withId(R.id.rvList)).perform(click());
        Activity activity = getActivityInstance();
        boolean b = (activity instanceof DetailedActivity);
        assertTrue(b);
    }


    @Test
    public void appNameDisplayed_inActionBar() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
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

    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Activity currentActivity;
            Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = (Activity) resumedActivities.iterator().next();
                activity[0] = currentActivity;
            }
        });
        return activity[0];
    }

}
