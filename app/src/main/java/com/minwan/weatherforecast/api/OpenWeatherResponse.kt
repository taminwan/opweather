package com.minwan.weatherforecast.api

import com.google.gson.annotations.SerializedName
import com.minwan.weatherforecast.model.OPWeatherListItemResponse

data class OpenWeatherResponse(
  @SerializedName("cod") val code: String = "",
  @SerializedName("list") val items: List<OPWeatherListItemResponse> = emptyList(),
)

