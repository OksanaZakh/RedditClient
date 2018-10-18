package com.example.ozakharc.redditclient.kotlintest

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import com.example.ozakharc.redditclient.helpers.ProgressIdlingResource
import com.example.ozakharc.redditclient.helpers.SwipeUpHelper
import com.example.ozakharc.redditclient.utils.Constants

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.ozakharc.redditclient.MainActivity
import com.example.ozakharc.redditclient.R
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not

@RunWith(AndroidJUnit4::class)
class MainActivityToastTestKt {


    var idlingResource: IdlingResource? = null

    @Rule @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun setup() {
        idlingResource = ProgressIdlingResource(activityTestRule.activity)
        IdlingRegistry.getInstance().register(idlingResource!!)
    }

    @After
    fun unregister() {
        IdlingRegistry.getInstance().unregister(idlingResource!!)
    }


    @Test
    fun showMessage_whenNoInternetConnection() {
        val numItemsLoaded = 20
        onView(ViewMatchers.withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(numItemsLoaded - 1))
        activityTestRule.activity.connection.setNotAvailable()
        onView(ViewMatchers.withId(R.id.rvList)).perform(SwipeUpHelper.swipeUp())
        onView(withText(Constants.NO_INTERNET_MESSAGE))
                .inRoot(withDecorView(not<View>(`is`<View>(activityTestRule.activity.window
                        .decorView)))).check(matches(isDisplayed()))
    }


}
