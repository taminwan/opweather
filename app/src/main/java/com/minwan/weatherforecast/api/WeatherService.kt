package com.minwan.weatherforecast.api

interface WeatherService {
  suspend fun getWeatherForecastForRegion(
    regionName: String,
    cnt: Int,
    tempUnit: String,
    appId: String
  ): WeatherResponse
}