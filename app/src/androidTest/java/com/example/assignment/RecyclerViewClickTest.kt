package com.example.assignment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.registerIdlingResources
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewClickTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isDetailFragmentVisible() {

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition<MeteorsListAdapter.MeteorsViewHolder>(0))

        onView(withId(R.id.recycler))
            .perform(actionOnItemAtPosition<MeteorsListAdapter.MeteorsViewHolder>(0, click()))

        onView(withId(R.id.map)).check(matches(isDisplayed()))
    }

}