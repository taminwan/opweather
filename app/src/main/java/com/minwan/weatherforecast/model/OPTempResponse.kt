package com.minwan.weatherforecast.model

import com.google.gson.annotations.SerializedName
import com.minwan.weatherforecast.helper.Cons
import java.lang.StringBuilder
import kotlin.math.roundToInt

/**
 * Model class for a OpenWeather's response for "temp" field
 */
data class OPTempResponse(
  @field:SerializedName("day") val day: Float,
  @field:SerializedName("min") val min: Float,
  @field:SerializedName("max") val max: Float,
  @field:SerializedName("night") val night: Float,
  @field:SerializedName("eve") val eve: Float,
  @field:SerializedName("morn") val morn: Float,
) {
  fun getDisplayTemperature(): String {
    val tempStringBuilder = StringBuilder()
    tempStringBuilder.append(day.roundToInt().toString())
    tempStringBuilder.append(Cons.DEFAULT_QUERY_UNITS.displayUnit)
    return tempStringBuilder.toString()
  }
}
