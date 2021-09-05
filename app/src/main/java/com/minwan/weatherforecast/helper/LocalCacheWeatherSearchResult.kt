package com.minwan.weatherforecast.helper

import androidx.collection.LruCache
import com.minwan.weatherforecast.model.WeatherSearchResult
import java.util.Calendar

class WeatherSearchResponseCache private constructor() {

  private var cache: LruCache<String, WeatherSearchResult> = LruCache(Cons.MAX_RESULT_CACHE_SIZE)

  companion object {
    private var INSTANCE: WeatherSearchResponseCache? = null
    fun getInstance(): WeatherSearchResponseCache =
      INSTANCE
        ?: synchronized(this) {
          return INSTANCE
            ?: WeatherSearchResponseCache()
              .also { INSTANCE = it }
        }
  }

  fun generateCacheKey(location: String, calendar: Calendar): String {
    val builder = StringBuilder()
    builder.append(location.uppercase())
    builder.append(calendar.get(Calendar.YEAR))
    builder.append(calendar.get(Calendar.DAY_OF_YEAR))
    builder.append(calendar.get(Calendar.HOUR_OF_DAY))
    return builder.toString()
  }

  fun putWeatherResult(location: String, result: WeatherSearchResult) {
    cache.put(generateCacheKey(location, Calendar.getInstance()), result)
  }

  fun getWeatherResult(location: String): WeatherSearchResult? {
    return cache.get(generateCacheKey(location, Calendar.getInstance()))
  }
}