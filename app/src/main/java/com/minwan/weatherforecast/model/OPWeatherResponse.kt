package com.minwan.weatherforecast.model

import com.google.gson.annotations.SerializedName

/**
 * Model class for a OpenWeather's response for "weather" field
 */
data class OPWeatherResponse(
  @field:SerializedName("id") val id: Long,
  @field:SerializedName("main") val main: String,
  @field:SerializedName("description") val description: String,
  @field:SerializedName("icon") val icon: String,
) {
  fun isValid(): Boolean {
    return description.isNotBlank()
  }
}
