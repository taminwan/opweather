package com.minwan.weatherforecast

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
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
    mockWebServer.enqueue(
      MockResponse()
        .setResponseCode(200)
        .setBody(
          "{\n" +
            "\t\"cod\": \"200\",\n" +
            "\t\"message\": 7.254712,\n" +
            "\t\"cnt\": 7,\n" +
            "\t\"list\": [{\n" +
            "\t\t\t\"dt\": 1629028800,\n" +
            "\t\t\t\"sunrise\": 1629002776,\n" +
            "\t\t\t\"sunset\": 1629055436,\n" +
            "\t\t\t\"temp\": {\n" +
            "\t\t\t\t\"day\": 22.79,\n" +
            "\t\t\t\t\"min\": 15.54,\n" +
            "\t\t\t\t\"max\": 22.91,\n" +
            "\t\t\t\t\"night\": 17.44,\n" +
            "\t\t\t\t\"eve\": 19.14,\n" +
            "\t\t\t\t\"morn\": 15.91\n" +
            "\t\t\t},\n" +
            "\t\t\t\"feels_like\": {\n" +
            "\t\t\t\t\"day\": 22.61,\n" +
            "\t\t\t\t\"night\": 17.25,\n" +
            "\t\t\t\t\"eve\": 18.75,\n" +
            "\t\t\t\t\"morn\": 15.88\n" +
            "\t\t\t},\n" +
            "\t\t\t\"pressure\": 1012,\n" +
            "\t\t\t\"humidity\": 57,\n" +
            "\t\t\t\"weather\": [{\n" +
            "\t\t\t\t\"id\": 803,\n" +
            "\t\t\t\t\"main\": \"Clouds\",\n" +
            "\t\t\t\t\"description\": \"broken clouds\",\n" +
            "\t\t\t\t\"icon\": \"04d\"\n" +
            "\t\t\t}],\n" +
            "\t\t\t\"speed\": 6.59,\n" +
            "\t\t\t\"deg\": 261,\n" +
            "\t\t\t\"gust\": 9.29,\n" +
            "\t\t\t\"clouds\": 71,\n" +
            "\t\t\t\"pop\": 0.02\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"dt\": 1629115200,\n" +
            "\t\t\t\"sunrise\": 1629089271,\n" +
            "\t\t\t\"sunset\": 1629141717,\n" +
            "\t\t\t\"temp\": {\n" +
            "\t\t\t\t\"day\": 18.72,\n" +
            "\t\t\t\t\"min\": 13.38,\n" +
            "\t\t\t\t\"max\": 19.43,\n" +
            "\t\t\t\t\"night\": 15.43,\n" +
            "\t\t\t\t\"eve\": 18.26,\n" +
            "\t\t\t\t\"morn\": 13.62\n" +
            "\t\t\t},\n" +
            "\t\t\t\"feels_like\": {\n" +
            "\t\t\t\t\"day\": 17.98,\n" +
            "\t\t\t\t\"night\": 14.8,\n" +
            "\t\t\t\t\"eve\": 17.53,\n" +
            "\t\t\t\t\"morn\": 13.07\n" +
            "\t\t\t},\n" +
            "\t\t\t\"pressure\": 1016,\n" +
            "\t\t\t\"humidity\": 51,\n" +
            "\t\t\t\"weather\": [{\n" +
            "\t\t\t\t\"id\": 804,\n" +
            "\t\t\t\t\"main\": \"Clouds\",\n" +
            "\t\t\t\t\"description\": \"overcast clouds\",\n" +
            "\t\t\t\t\"icon\": \"04d\"\n" +
            "\t\t\t}],\n" +
            "\t\t\t\"speed\": 5.85,\n" +
            "\t\t\t\"deg\": 311,\n" +
            "\t\t\t\"gust\": 10.93,\n" +
            "\t\t\t\"clouds\": 100,\n" +
            "\t\t\t\"pop\": 0.2\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}"
        )
    )
    Thread.sleep(1000)
  }

  @After
  @Throws(IOException::class)
  fun teardown() {
    mockWebServer.shutdown()
  }

  @Test
  fun searLocation_success_2_items() {
    onView(ViewMatchers.withId(id.edtSearch)).perform(ViewActions.typeText("LonDon"))
    onView(ViewMatchers.withId(id.btnGet)).perform(ViewActions.click())

    Thread.sleep(1000)

    onView(withText("Sorry. Something goes wrong!")).check(doesNotExist())
    onView(ViewMatchers.withId(id.emptyList))
      .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    onView(withText("Date: Sun, 15 Aug 2021")).check(ViewAssertions.matches(isDisplayed()))
    onView(withText("Average temperature: 23°C")).check(ViewAssertions.matches(isDisplayed()))
    onView(withText("Pressure: 1012")).check(ViewAssertions.matches(isDisplayed()))
    onView(withText("Humidity: 57%")).check(ViewAssertions.matches(isDisplayed()))
    onView(withText("Description: broken clouds")).check(ViewAssertions.matches(isDisplayed()))

    onView(withText("Date: Mon, 16 Aug 2021")).check(ViewAssertions.matches(isDisplayed()))
    onView(withText("Average temperature: 19°C")).check(ViewAssertions.matches(isDisplayed()))
    onView(withText("Pressure: 1016")).check(ViewAssertions.matches(isDisplayed()))
    onView(withText("Humidity: 57%")).check(ViewAssertions.matches(isDisplayed()))
    onView(withText("Description: overcast clouds")).check(ViewAssertions.matches(isDisplayed()))
  }
}