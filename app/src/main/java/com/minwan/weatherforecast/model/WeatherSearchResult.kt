package com.minwan.weatherforecast.model

/**
 * WeatherSearchResult from a search, which contains List<WeatherSearchListItem> holding query response data
 * and a String of network error state.
 */
sealed class WeatherSearchResult {
  data class Success(val list: List<OPWeatherListItemResponse>) : WeatherSearchResult()
  data class Error(val error: Exception) : WeatherSearchResult()
}
