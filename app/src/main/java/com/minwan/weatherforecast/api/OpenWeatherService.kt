package com.minwan.weatherforecast.api

import com.minwan.weatherforecast.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
  /**
   * Get region weather forecast
   */
  @GET("/data/2.5/forecast/daily")
  suspend fun getWeatherForecastForRegion(
    @Query("q") regionName: String,
    @Query("cnt") cnt: Int,
    @Query("units") tempUnit: String,
    @Query("appId") appId: String
  ): OpenWeatherResponse

  companion object {
    var HOST = if (BuildConfig.IS_MOCK_TEST_ENABLE) {
      BuildConfig.HOST_MOCK
    } else {
      BuildConfig.HOST_PROD
    }


    fun create(): OpenWeatherService {
      val logger = HttpLoggingInterceptor()
      logger.level = Level.BASIC

      val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()
      return Retrofit.Builder()
        .baseUrl(HOST)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenWeatherService::class.java)
    }
  }
}