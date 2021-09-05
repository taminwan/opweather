package com.minwan.weatherforecast.api

import com.google.gson.annotations.SerializedName
import com.minwan.weatherforecast.model.WeatherListItemResponse

data class WeatherResponse(
  @SerializedName("cod") val code: String = "",
  @SerializedName("list") val items: List<WeatherListItemResponse> = emptyList(),
)

