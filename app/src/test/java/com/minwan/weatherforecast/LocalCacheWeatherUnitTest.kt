package com.minwan.weatherforecast

import com.minwan.weatherforecast.helper.WeatherSearchResponseCache
import com.minwan.weatherforecast.model.WeatherSearchResult
import org.junit.*
import java.util.Calendar

class LocalCacheWeatherUnitTest {

  companion object {
    val CITIES = mutableListOf(
      "saigon",
      "hanoi",
      "london",
      "newyork",
      "lisbon",
      "tokyo",
      "osaka",
    )
  }

  @Test
  fun generateCacheKey_isCorrect() {
    val location = " saiGon "
    val calendarForTesting = Calendar.getInstance()
    calendarForTesting.set(Calendar.YEAR, 2021)
    calendarForTesting.set(Calendar.DAY_OF_YEAR, 26)
    calendarForTesting.set(Calendar.HOUR_OF_DAY, 12)

    val cacheKey = WeatherSearchResponseCache.getInstance().generateCacheKey(location, calendarForTesting)
    Assert.assertEquals("SAIGON20212612", cacheKey)
  }

  @Test
  fun cacheResponseFromLondon_randomCapitalizeChars() {
    val location = "LonDON"
    val calendarForTesting = Calendar.getInstance()
    val year = calendarForTesting.get(Calendar.YEAR)
    val dayOfYear = calendarForTesting.get(Calendar.DAY_OF_YEAR)
    val hourOfDay = calendarForTesting.get(Calendar.HOUR_OF_DAY)

    WeatherSearchResponseCache.getInstance().clear()

    val cacheKey = WeatherSearchResponseCache.getInstance().generateCacheKey(location, calendarForTesting)
    Assert.assertEquals("LONDON$year$dayOfYear$hourOfDay", cacheKey)

    WeatherSearchResponseCache.getInstance().putWeatherResult(location, WeatherSearchResult.Success(emptyList()))
    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("london"))
    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("London"))
    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("LOndon"))
    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("LONdon"))
    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("LONDon"))
    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("LONDOn"))
    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("LONDON"))
    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("LondoN"))
  }

  @Test
  fun cacheNotFound() {
    WeatherSearchResponseCache.getInstance().clear()

    CITIES.forEach { city ->
      WeatherSearchResponseCache.getInstance().putWeatherResult(city, WeatherSearchResult.Success(emptyList()))
    }

    Assert.assertNull(WeatherSearchResponseCache.getInstance().getWeatherResult("oslo"))
    Assert.assertNull(WeatherSearchResponseCache.getInstance().getWeatherResult("DENVER"))
    Assert.assertNull(WeatherSearchResponseCache.getInstance().getWeatherResult("Moscow"))
  }

  @Test
  fun cacheFound_WithSpaceKeyword() {
    WeatherSearchResponseCache.getInstance().clear()

    CITIES.forEach { city ->
      WeatherSearchResponseCache.getInstance().putWeatherResult(city, WeatherSearchResult.Success(emptyList()))
    }

    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("  Saigon "))
  }

  @Test
  fun cacheNotFound_ThenFoundAfterAdd() {
    WeatherSearchResponseCache.getInstance().clear()

    Assert.assertNull(WeatherSearchResponseCache.getInstance().getWeatherResult("saigon"))

    CITIES.forEach { city ->
      WeatherSearchResponseCache.getInstance().putWeatherResult(city, WeatherSearchResult.Success(emptyList()))
    }

    Assert.assertNotNull(WeatherSearchResponseCache.getInstance().getWeatherResult("saigon"))
  }
}