package com.minwan.weatherforecast.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.minwan.weatherforecast.model.WeatherDisplayData
import com.minwan.weatherforecast.ui.viewholder.OPWeatherViewHolder

/**
 * Adapter for the list of weather forecast items.
 */
class WeatherForecastAdapter : ListAdapter<WeatherDisplayData, OPWeatherViewHolder>(WEATHER_COMPARATOR) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OPWeatherViewHolder {
    return OPWeatherViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: OPWeatherViewHolder, position: Int) {
    val repoItem = getItem(position)
    if (repoItem != null) {
      holder.bind(repoItem)
    }
  }

  companion object {
    private val WEATHER_COMPARATOR = object : ItemCallback<WeatherDisplayData>() {

      override fun areItemsTheSame(
        oldItem: WeatherDisplayData,
        newItem: WeatherDisplayData
      ): Boolean =
        oldItem == newItem

      override fun areContentsTheSame(
        oldItem: WeatherDisplayData,
        newItem: WeatherDisplayData
      ): Boolean =
        oldItem.getDateTime() == newItem.getDateTime() &&
          oldItem.getWeatherDescription() == newItem.getWeatherDescription() &&
          oldItem.getHumidity() == newItem.getHumidity() &&
          oldItem.getPressure() == newItem.getPressure() &&
          oldItem.getTemperature() == newItem.getTemperature()
    }
  }
}