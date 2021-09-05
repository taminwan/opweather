package com.minwan.weatherforecast.helper

import com.minwan.weatherforecast.R
import com.minwan.weatherforecast.application.OPWeatherApplication

enum class UiErrorCode(val errorMessage: String) {
  NO_ERROR(""),
  SHORT_LOCATION_SEARCH(
    OPWeatherApplication.getInstance()
      .getString(R.string.search_require_min_search_length, Cons.MIN_LENGTH_REGION_NAME.toString()),
  )
}