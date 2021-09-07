package com.minwan.weatherforecast.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.minwan.weatherforecast.R.id
import com.minwan.weatherforecast.R.layout
import com.minwan.weatherforecast.model.WeatherDisplayData
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

  private var weatherForecastItem: WeatherDisplayData? = null

  fun bind(data: WeatherDisplayData?) {
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

  private fun showWeatherData(weatherForecastItem: WeatherDisplayData) {
    val res = itemView.context.resources
    this.weatherForecastItem = weatherForecastItem
    weatherForecastItem.getWeather()?.let {
      tvDate.text = weatherForecastItem.getTimeDisplayString(res)
      listOfTextView.forEach { it.isGone = false }
      tvHumidity.text = weatherForecastItem.getHumidityDisplayString(res)
      tvPressure.text = weatherForecastItem.getPressureDisplayString(res)
      tvTemperature.text = weatherForecastItem.getTemperatureDisplayString(res)
      tvDescription.text = weatherForecastItem.getDescriptionDisplayString(res)
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