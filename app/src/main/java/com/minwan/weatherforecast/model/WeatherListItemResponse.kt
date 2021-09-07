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
) : WeatherDisplayData {
  override fun getDateTime(): Long {
    return dt
  }

  override fun getWeather(): WeatherData? {
    return weathers.firstOrNull()
  }

  override fun getWeatherDescription(): String? {
    return getWeather()?.description
  }

  override fun getTemperature(): String {
    return temp.getDisplayTemperature()
  }

  override fun getHumidity(): String {
    return humidity.roundToInt().toString()
  }

  override fun getPressure(): String {
    return pressure.roundToInt().toString()
  }
}
