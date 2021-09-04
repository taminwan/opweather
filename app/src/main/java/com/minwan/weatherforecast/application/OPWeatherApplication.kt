package com.minwan.weatherforecast.application

import android.app.Application
import com.minwan.weatherforecast.loader.OpenWeatherLoader

class OPWeatherApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    OpenWeatherLoader.init()
  }
}