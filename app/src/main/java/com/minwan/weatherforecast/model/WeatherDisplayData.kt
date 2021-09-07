package com.minwan.weatherforecast.model

import android.content.res.Resources
import com.minwan.weatherforecast.R.string
import com.minwan.weatherforecast.helper.TimeHelper

/**
 * Interface for displaying data on weather list item
 */
interface WeatherDisplayData {
  fun getDateTime(): Long

  fun getWeather(): WeatherData?

  fun getWeatherDescription(): String?

  fun getTemperature(): String

  fun getHumidity(): String

  fun getPressure(): String

  fun getTimeDisplayString(res: Resources): String {
    return res.getString(string.display_date, TimeHelper.convertEpochTimeToDisplayText(getDateTime()))
  }

  fun getHumidityDisplayString(res: Resources): String {
    return res.getString(string.display_humidity, getHumidity())
  }

  fun getPressureDisplayString(res: Resources): String {
    return res.getString(string.display_pressure, getPressure())
  }

  fun getTemperatureDisplayString(res: Resources): String {
    return res.getString(string.display_temperature, getTemperature())
  }

  fun getDescriptionDisplayString(res: Resources): String {
    return res.getString(string.display_description, getWeatherDescription())
  }
}