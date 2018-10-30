package com.example.ozakharc.redditclient;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.detailed.DetailedActivity;
import com.example.ozakharc.redditclient.helpers.ActivityIdentification;
import com.example.ozakharc.redditclient.helpers.DrawableMatcher;
import com.example.ozakharc.redditclient.helpers.ProgressIdlingResource;
import com.example.ozakharc.redditclient.helpers.SwipeUpHelper;
import com.example.ozakharc.redditclient.utils.DateConverter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class DetailedActivityTest {

    private IdlingResource idlingResource;
    IdlingResource dialogIdlingResource;

    private ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(
            MainActivity.class);
    private ActivityTestRule<DetailedActivity> detailedActivityRule = new ActivityTestRule<>(
            DetailedActivity.class, false, false);

    @Rule
    public RuleChain chain = RuleChain.outerRule(mainActivityRule).around(detailedActivityRule);

    @Before
    public void setup() {
        idlingResource = new ProgressIdlingResource(mainActivityRule.getActivity());
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregister() {
        IdlingRegistry.getInstance().unregister(idlingResource);
        if (dialogIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(dialogIdlingResource);
        }
    }

    @Test
    public void appNameDisplayedInToolBar() {
        onView(withId(R.id.rvList)).perform(click());
        onView(withText(R.string.app_name)).check(matches(allOf(withParent(withId(R.id.toolbar)), isDisplayed())));
    }

    @Test
    public void navigateToMainActivity_backButtonWithProperDrawablePressed() {
        onView(withId(R.id.rvList)).perform(click());
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(allOf(DrawableMatcher.withDrawable(R.drawable.ic_left_arrow), isDisplayed()))));
        onView(allOf(isAssignableFrom(ImageView.class), withParent(isAssignableFrom(Toolbar.class)))).perform((click()));
        Activity activity = ActivityIdentification.getActivityInstance();
        boolean isNavigateToMain = (activity instanceof MainActivity);
        assertTrue(isNavigateToMain);
    }

    @Test
    public void navigateToMainActivity_whenBackPressClicked() {
        onView(ViewMatchers.withId(R.id.rvList)).perform(click());
        Espresso.pressBack();
        Activity activity = ActivityIdentification.getActivityInstance();
        boolean isNavigateToMain = (activity instanceof MainActivity);
        assertTrue(isNavigateToMain);
    }

    @Test
    public void checkDataVisibilityAndText() {
        int targetItemPosition = 3;

        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.actionOnItemAtPosition(targetItemPosition+1, click()));
        NewsItem item = mainActivityRule.getActivity().getAllNewsItems().get(targetItemPosition);

        onView(withId(R.id.tvAuthor)).check(matches(allOf(isDisplayed(), withText(item.getAuthor()))));
        onView(withId(R.id.tvTitle)).check(matches(allOf(isDisplayed(), withText(item.getTitle()))));

        if (!item.getSelftext().trim().isEmpty()) {
            onView(withId(R.id.tvDescription)).check(matches(allOf(isDisplayed(), withText(item.getSelftext()))));
        }
        if (URLUtil.isValidUrl(item.getThumbnail())) {
            onView(withId(R.id.ivPhoto)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.ivPhoto)).check(matches(not(isDisplayed())));
        }
        onView(withId(R.id.tvPostedBy)).check(matches(allOf(isDisplayed(), withText(R.string.posted_by))));
        onView(ViewMatchers.withId(R.id.llScrollContainer)).perform(SwipeUpHelper.swipeUp());
        onView(withId(R.id.tvDateText)).check(matches(allOf(isDisplayed(), withText(R.string.date))));
        String date = DateConverter.getStringDate(item.getCreatedUtc());
        onView(withId(R.id.tvDate)).check(matches(allOf(isDisplayed(), withText(date))));
        if (!item.getUrl().trim().isEmpty()) {
            onView(withId(R.id.tvLink)).check(matches(allOf(isDisplayed(), withText(item.getUrl()))));
        }
    }

    @Test
    public void imageDialog_checkVisibilityAndClick() {
        //Find item with image
        int targetPosition = 0;
        onView(ViewMatchers.withId(R.id.rvList)).check(matches(isDisplayed()));
        NewsItem item = mainActivityRule.getActivity().getAllNewsItems().get(targetPosition);
        while (!URLUtil.isValidUrl(item.getThumbnail())) {
            targetPosition++;
            item = mainActivityRule.getActivity().getAllNewsItems().get(targetPosition);
        }

        //Do to Detailed Activity and click on image
        onView(withId(R.id.rvList)).perform(RecyclerViewActions.actionOnItemAtPosition(targetPosition+1, click()));
        onView(withId(R.id.tvTitle)).check(matches(allOf(isDisplayed(), withText(item.getTitle()))));
        onView(withId(R.id.ivPhoto)).perform(click());

        //Register Idling recourse for image loading
        dialogIdlingResource = new ProgressIdlingResource((DetailedActivity) ActivityIdentification.getActivityInstance());
        IdlingRegistry.getInstance().register(dialogIdlingResource);

        //Check if the dialog is displays and disappears after clicking
        onView(withId(R.id.image)).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.image)).check(doesNotExist());
    }
}
