package com.moji.menulog

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.moji.menulog.presentation.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FragmentNavUITest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun click_on_postcode_navigate_to_restaurant() {
        Espresso.onView(ViewMatchers.withId(R.id.editPostcode))
                .perform(typeText("se19"))
        Espresso.onView(ViewMatchers.withId(R.id.btnFind))
                .perform(click())
        Espresso.onView(ViewMatchers.withText("restaurants in se19"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
