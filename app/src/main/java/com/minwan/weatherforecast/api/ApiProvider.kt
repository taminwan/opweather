package com.minwan.weatherforecast.api

import com.minwan.weatherforecast.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

  private fun getHttpClient(): OkHttpClient {
    val logger = HttpLoggingInterceptor()
    logger.level = Level.BASIC

    return OkHttpClient.Builder()
      .addInterceptor(logger)
      .build()
  }

  private val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.HOST)
    .client(getHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  fun <T> createApiService(clazz: Class<T>): T {
    return retrofit.create(clazz)
  }
}