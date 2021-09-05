package com.minwan.weatherforecast

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.minwan.weatherforecast.screen.SearchRegionWeatherActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4ClassRunner::class)
class ErrorApiInstrumentedTest {

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
  fun searchLocation_failed_404_showNoResults() {
    mockWebServer.enqueue(
      MockResponse()
        .setResponseCode(404)
    )
    onView(ViewMatchers.withId(R.id.edtSearch)).perform(ViewActions.typeText("LonDon"))
    onView(ViewMatchers.withId(R.id.btnGet)).perform(ViewActions.click())

    Thread.sleep(100)

    onView(withText("Sorry. Something goes wrong!"))

    onView(ViewMatchers.withId(R.id.emptyList))
      .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
      .check(ViewAssertions.matches(withText("No results.")))
  }

  @Test
  fun searchLocation_failed_300_showNoResults() {
    mockWebServer.enqueue(
      MockResponse()
        .setResponseCode(300)
    )

    onView(ViewMatchers.withId(R.id.edtSearch)).perform(ViewActions.typeText("SaiGon"))
    onView(ViewMatchers.withId(R.id.btnGet)).perform(ViewActions.click())

    Thread.sleep(100)

    onView(withText("Sorry. Something goes wrong!"))

    onView(ViewMatchers.withId(R.id.emptyList))
      .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
      .check(ViewAssertions.matches(withText("No results.")))
  }

  @Test
  fun searchLocation_failed_400_showNoResults() {
    mockWebServer.enqueue(
      MockResponse()
        .setResponseCode(400)
    )
    onView(ViewMatchers.withId(R.id.edtSearch)).perform(ViewActions.typeText("LonDon"))
    onView(ViewMatchers.withId(R.id.btnGet)).perform(ViewActions.click())

    Thread.sleep(100)

    onView(withText("Sorry. Something goes wrong!"))

    onView(ViewMatchers.withId(R.id.emptyList))
      .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
      .check(ViewAssertions.matches(withText("No results.")))
  }
}