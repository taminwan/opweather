package com.minwan.weatherforecast.application

import android.app.Application
import com.minwan.weatherforecast.loader.OpenWeatherLoader

class OPWeatherApplication : Application() {

  companion object {
    private lateinit var INSTANCE: OPWeatherApplication
    fun getInstance() = INSTANCE
  }

  override fun onCreate() {
    super.onCreate()
    INSTANCE = this
    OpenWeatherLoader.init()
  }
}