package com.minwan.weatherforecast

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.minwan.weatherforecast.R.id
import com.minwan.weatherforecast.helper.Cons
import com.minwan.weatherforecast.screen.SearchRegionWeatherActivity
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4ClassRunner::class)
class UserBadBehaviorTesting {

  @Rule
  @JvmField
  var activityRule: ActivityTestRule<SearchRegionWeatherActivity> =
    ActivityTestRule(SearchRegionWeatherActivity::class.java)

  @Test
  fun searLocation_blankInput_showNoResult() {
    onView(ViewMatchers.withId(id.edtSearch)).perform(ViewActions.typeText("   "))
    onView(ViewMatchers.withId(id.btnGet)).perform(ViewActions.click())

    Thread.sleep(500)

    onView(withText("Sorry. Something goes wrong!")).check(ViewAssertions.doesNotExist())
    onView(withText("At least input " + Cons.MIN_LENGTH_REGION_NAME + " alphabet character(s)"))

    onView(ViewMatchers.withId(id.emptyList))
      .check(ViewAssertions.matches(isDisplayed()))
      .check(ViewAssertions.matches(withText("No results.")))
  }

  @Test
  fun searLocation_1_character_showNoResult() {
    onView(ViewMatchers.withId(id.edtSearch)).perform(ViewActions.typeText("a"))
    onView(ViewMatchers.withId(id.btnGet)).perform(ViewActions.click())

    Thread.sleep(500)

    onView(withText("Sorry. Something goes wrong!"))
      .check(ViewAssertions.doesNotExist())
    onView(withText("At least input " + Cons.MIN_LENGTH_REGION_NAME + " alphabet character(s)"))

    onView(ViewMatchers.withId(id.emptyList))
      .check(ViewAssertions.matches(isDisplayed()))
      .check(ViewAssertions.matches(withText("No results.")))
  }

  @Test
  fun searLocation_2_characters_showNoResult() {
    onView(ViewMatchers.withId(id.edtSearch)).perform(ViewActions.typeText("ab"))
    onView(ViewMatchers.withId(id.btnGet)).perform(ViewActions.click())

    Thread.sleep(500)

    onView(withText("Sorry. Something goes wrong!"))
      .check(ViewAssertions.doesNotExist())
    onView(withText("At least input " + Cons.MIN_LENGTH_REGION_NAME + " alphabet character(s)"))

    onView(ViewMatchers.withId(id.emptyList))
      .check(ViewAssertions.matches(isDisplayed()))
      .check(ViewAssertions.matches(withText("No results.")))
  }

  @Test
  fun searLocation_3_characters_showNoResult() {
    onView(ViewMatchers.withId(id.edtSearch)).perform(ViewActions.typeText("abc"))
    onView(ViewMatchers.withId(id.btnGet)).perform(ViewActions.click())

    Thread.sleep(500)

    onView(withText("Sorry. Something goes wrong!"))
    onView(withText("At least input " + Cons.MIN_LENGTH_REGION_NAME + " alphabet character(s)"))
      .check(ViewAssertions.doesNotExist())


    onView(ViewMatchers.withId(id.emptyList))
      .check(ViewAssertions.matches(isDisplayed()))
      .check(ViewAssertions.matches(withText("No results.")))
  }
}