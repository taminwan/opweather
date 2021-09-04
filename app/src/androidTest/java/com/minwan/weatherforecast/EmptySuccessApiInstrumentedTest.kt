package com.minwan.weatherforecast

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.minwan.weatherforecast.screen.SearchRegionWeatherActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4ClassRunner::class)
class EmptySuccessApiInstrumentedTest {

  private val mockWebServer = MockWebServer()

  @Rule
  @JvmField
  var activityRule: ActivityTestRule<SearchRegionWeatherActivity> =
    ActivityTestRule(SearchRegionWeatherActivity::class.java)

  @Before
  @Throws(IOException::class, InterruptedException::class)
  fun setup() {
    mockWebServer.start(5555)
    mockWebServer.enqueue(
      MockResponse()
        .setResponseCode(200)
        .setBody(FileReaderHelper.readStringFromFile("success_empty_response.json"))
    )
  }

  @After
  @Throws(IOException::class)
  fun teardown() {
    mockWebServer.shutdown()
  }

  @Test
  fun searchLocation_success_showNoResult() {
    onView(ViewMatchers.withId(R.id.edtSearch)).perform(ViewActions.typeText("LonDon"))
    onView(ViewMatchers.withId(R.id.btnGet)).perform(ViewActions.click())
    onView(ViewMatchers.withText("Sorry. Something goes wrong!")).check(doesNotExist())
    onView(ViewMatchers.withId(R.id.emptyList))
      .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
      .check(ViewAssertions.matches(ViewMatchers.withText("No results.")))
  }
}