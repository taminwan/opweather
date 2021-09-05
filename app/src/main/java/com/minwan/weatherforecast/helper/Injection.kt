package com.minwan.weatherforecast.helper

import androidx.lifecycle.ViewModelProvider.Factory
import androidx.savedstate.SavedStateRegistryOwner
import com.minwan.weatherforecast.api.ApiProvider
import com.minwan.weatherforecast.api.OpenWeatherService
import com.minwan.weatherforecast.repo.OpenWeatherRepository
import com.minwan.weatherforecast.viewmodel.ViewModelFactory

/**
 * Class that handles object creation.
 */
object Injection {
  /**
   * Creates an instance of [OpenWeatherRepository] based on the [OpenWeatherService]
   */
  private fun provideOpenWeatherRepository(): OpenWeatherRepository {
    return OpenWeatherRepository(ApiProvider.createApiService(OpenWeatherService::class.java))
  }

  /**
   * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
   * [ViewModel] objects.
   */
  fun provideViewModelFactory(owner: SavedStateRegistryOwner): Factory {
    return ViewModelFactory(owner, provideOpenWeatherRepository())
  }
}