package com.minwan.weatherforecast.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.minwan.weatherforecast.R.id
import com.minwan.weatherforecast.R.layout
import com.minwan.weatherforecast.R.string
import com.minwan.weatherforecast.helper.TimeHelper
import com.minwan.weatherforecast.model.WeatherListItemResponse

/**
 * View Holder for a [WeatherListItemResponse] RecyclerView list item.
 */
class OPWeatherViewHolder(view: View) : ViewHolder(view) {
  private val tvDate: TextView = view.findViewById(id.tvDate)
  private val tvTemperature: TextView = view.findViewById(id.tvTemperature)
  private val tvPressure: TextView = view.findViewById(id.tvPressure)
  private val tvHumidity: TextView = view.findViewById(id.tvHumidity)
  private val tvDescription: TextView = view.findViewById(id.tvDescription)

  private val listOfTextView = listOf(
    tvTemperature,
    tvPressure,
    tvHumidity,
    tvDescription
  )

  private var weatherForecastItem: WeatherListItemResponse? = null

  fun bind(data: WeatherListItemResponse?) {
    if (data == null) {
      tvDate.text = ""
      listOfTextView.forEach {
        it.text = ""
        it.isGone = true
      }
    } else {
      showWeatherData(data)
    }
  }

  private fun showWeatherData(weatherForecastItem: WeatherListItemResponse) {
    val res = itemView.context.resources
    this.weatherForecastItem = weatherForecastItem
    weatherForecastItem.getWeather()?.let {
      tvDate.text =
        res.getString(string.display_date, TimeHelper.convertEpochTimeToDisplayText(weatherForecastItem.dt))
      listOfTextView.forEach { it.isGone = false }
      tvHumidity.text = res.getString(string.display_humidity, weatherForecastItem.getDisplayHumidity())
      tvPressure.text = res.getString(string.display_pressure, weatherForecastItem.getDisplayPressure())
      tvTemperature.text = res.getString(string.display_temperature, weatherForecastItem.getDisplayTemperature())
      tvDescription.text =
        res.getString(string.display_description, weatherForecastItem.getDisplayWeatherDescription())
    }
  }

  companion object {
    fun create(parent: ViewGroup): OPWeatherViewHolder {
      val view = LayoutInflater.from(parent.context)
        .inflate(layout.open_weather_item_layout, parent, false)
      return OPWeatherViewHolder(view)
    }
  }
}