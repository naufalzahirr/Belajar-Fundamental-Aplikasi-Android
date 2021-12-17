package com.example.submissionakhiraplikasigithubuser.ui

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.submissionakhiraplikasigithubuser.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun allUITest() {
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("GG")
        )

        Thread.sleep(2000)
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_users))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    click()
                )
            )

        Thread.sleep(2000)
        onView(withId(R.id.starButton)).perform(click())
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.view_pager)).perform(swipeRight())

        onView(isRoot()).perform(pressBack())

        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText("Favorite")).perform(click())
    }
}