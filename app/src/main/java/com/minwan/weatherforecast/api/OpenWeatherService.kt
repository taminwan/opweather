package com.minwan.weatherforecast.api

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService : WeatherService {
  /**
   * Get region weather forecast
   */
  @GET("/data/2.5/forecast/daily")
  override suspend fun getWeatherForecastForRegion(
    @Query("q") regionName: String,
    @Query("cnt") cnt: Int,
    @Query("units") tempUnit: String,
    @Query("appId") appId: String
  ): WeatherResponse
}