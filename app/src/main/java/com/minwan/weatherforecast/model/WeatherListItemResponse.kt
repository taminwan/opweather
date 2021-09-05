package com.minwan.weatherforecast.model

import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

/**
 * Model class for a OpenWeather's response list item
 */
data class WeatherListItemResponse(
  @field:SerializedName("dt") val dt: Long,
  @field:SerializedName("deg") val deg: Float,
  @field:SerializedName("pressure") val pressure: Float,
  @field:SerializedName("humidity") val humidity: Float,
  @field:SerializedName("weather") val weathers: List<WeatherData>,
  @field:SerializedName("temp") val temp: OPTempResponse,
) {
  fun getWeather(): WeatherData? {
    return weathers.firstOrNull()
  }

  fun getDisplayWeatherDescription(): String? {
    return getWeather()?.description
  }

  fun getDisplayTemperature(): String {
    return temp.getDisplayTemperature()
  }

  fun getDisplayHumidity(): String {
    return humidity.roundToInt().toString()
  }

  fun getDisplayPressure(): String {
    return pressure.roundToInt().toString()
  }
}
