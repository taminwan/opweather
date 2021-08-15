package com.minwan.weatherforecast.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.minwan.weatherforecast.repo.OpenWeatherRepository

/**
 * Factory for ViewModels
 */
class ViewModelFactory(
  owner: SavedStateRegistryOwner,
  private val repository: OpenWeatherRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T {
    if (modelClass.isAssignableFrom(SearchRepositoriesViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return SearchRepositoriesViewModel(repository, handle) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
