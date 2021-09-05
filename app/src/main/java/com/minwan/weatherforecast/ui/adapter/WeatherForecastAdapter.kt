package com.minwan.weatherforecast.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.minwan.weatherforecast.model.WeatherListItemResponse
import com.minwan.weatherforecast.ui.viewholder.OPWeatherViewHolder

/**
 * Adapter for the list of weather forecast items.
 */
class WeatherForecastAdapter : ListAdapter<WeatherListItemResponse, OPWeatherViewHolder>(WEATHER_COMPARATOR) {

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
    private val WEATHER_COMPARATOR = object : ItemCallback<WeatherListItemResponse>() {

      override fun areItemsTheSame(
        oldItem: WeatherListItemResponse,
        newItem: WeatherListItemResponse
      ): Boolean =
        oldItem.dt == newItem.dt && oldItem.getDisplayTemperature() == newItem.getDisplayTemperature()

      override fun areContentsTheSame(
        oldItem: WeatherListItemResponse,
        newItem: WeatherListItemResponse
      ): Boolean =
        oldItem == newItem
    }
  }
}