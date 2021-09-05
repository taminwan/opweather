package com.minwan.weatherforecast

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.minwan.weatherforecast.R.id
import com.minwan.weatherforecast.screen.SearchRegionWeatherActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4ClassRunner::class)
class SuccessApiInstrumentedTest {
  private val mockWebServer = MockWebServer()

  @Rule
  @JvmField
  var activityRule: ActivityTestRule<SearchRegionWeatherActivity> =
    ActivityTestRule(SearchRegionWeatherActivity::class.java)

  @Before
  @Throws(IOException::class, InterruptedException::class)
  fun setup() {
    mockWebServer.start(5555)
  }

  @After
  @Throws(IOException::class)
  fun teardown() {
    mockWebServer.shutdown()
  }

  @Test
  fun searchLocation_success_2_items() {
    mockWebServer.enqueue(
      MockResponse()
        .setResponseCode(200)
        .setBody(FileReaderHelper.readStringFromFile("success_2_items_response.json"))
    )

    onView(ViewMatchers.withId(id.edtSearch)).perform(ViewActions.typeText("London"))
    onView(ViewMatchers.withId(id.btnGet)).perform(ViewActions.click())

    onView(withText("Sorry. Something goes wrong!")).check(doesNotExist())
    onView(ViewMatchers.withId(id.emptyList))
      .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    onView(withText("Date: Sat, 04 Sep 2021")).check(matches(isDisplayed()))
    onView(withText("Average temperature: 18°C")).check(matches(isDisplayed()))
    onView(withText("Pressure: 1019")).check(matches(isDisplayed()))
    onView(withText("Humidity: 79%")).check(matches(isDisplayed()))
    onView(withText("Description: overcast clouds")).check(matches(isDisplayed()))

    onView(withText("Date: Sun, 05 Sep 2021")).check(matches(isDisplayed()))
    onView(withText("Average temperature: 21°C")).check(matches(isDisplayed()))
    onView(withText("Pressure: 1021")).check(matches(isDisplayed()))
    onView(withText("Humidity: 54%")).check(matches(isDisplayed()))
    onView(withText("Description: sky is clear")).check(matches(isDisplayed()))
  }
}