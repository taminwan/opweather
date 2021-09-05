package com.minwan.weatherforecast.repo

import android.util.Log
import com.minwan.weatherforecast.api.WeatherService
import com.minwan.weatherforecast.helper.Cons
import com.minwan.weatherforecast.helper.WeatherSearchResponseCache
import com.minwan.weatherforecast.loader.OpenWeatherLoader
import com.minwan.weatherforecast.model.WeatherSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

/**
 * Repository class that works with local and service data
 */
class OpenWeatherRepository(private val service: WeatherService) {

  private val TAG = "OpenWeatherRepository"

  private val searchResults = MutableSharedFlow<WeatherSearchResult>(replay = 1)

  /**
   * Search region's weather whose names match the newLocation, exposed as a stream of data that will emit
   * every time we get more data from the network.
   */
  suspend fun getSearchResultStream(newLocation: String): Flow<WeatherSearchResult> {
    Log.d("OpenWeatherRepository", "New location: $newLocation")
    val cacheWeatherResult = WeatherSearchResponseCache.getInstance().getWeatherResult(newLocation)
    if (cacheWeatherResult == null) {
      requestAndSaveData(newLocation)
    } else {
      searchResults.emit(cacheWeatherResult)
    }
    return searchResults
  }

  suspend fun getEmptyResultError(customMessage: String = ""): Flow<WeatherSearchResult> {
    searchResults.emit(WeatherSearchResult.Error(Exception(customMessage)))
    return searchResults
  }

  /**
   * Get service response and save response to cache
   */
  private suspend fun requestAndSaveData(newLocation: String): Boolean {
    var successful = false
    try {
      val response = service.getWeatherForecastForRegion(
        newLocation,
        Cons.DEFAULT_FORECAST_DAYS_RANGE,
        Cons.DEFAULT_QUERY_UNITS.name,
        OpenWeatherLoader.getOpenWeatherAppId()
      )
      Log.d("OpenWeatherRepository", "response $response")
      val weatherForecastItems = response.items
      val successResponse = WeatherSearchResult.Success(weatherForecastItems)
      searchResults.emit(successResponse)
      WeatherSearchResponseCache.getInstance().putWeatherResult(newLocation, successResponse)
      successful = true
    } catch (ex: IOException) {
      searchResults.emit(WeatherSearchResult.Error(ex))
    } catch (ex: HttpException) {
      searchResults.emit(WeatherSearchResult.Error(ex))
    } catch (ex: Exception) {
      Log.e(TAG, "Unhandled exception: $ex")
      searchResults.emit(WeatherSearchResult.Error(ex))
    }
    return successful
  }
}
