package com.minwan.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.minwan.weatherforecast.model.WeatherSearchResult
import com.minwan.weatherforecast.repo.OpenWeatherRepository
import com.minwan.weatherforecast.viewmodel.UiAction.Search
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

/**
 * ViewModel for the [SearchRegionWeatherActivity] screen.
 * The ViewModel works with the [OpenWeatherRepository] to get the data.
 */
class SearchRepositoriesViewModel(
  private val repository: OpenWeatherRepository,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  /**
   * Stream of immutable states representative of the UI.
   */
  val state: LiveData<UiState>

  /**
   * Processor of side effects from the UI which in turn feedback into [state]
   */
  val accept: (UiAction) -> Unit

  init {
    val queryLiveData =
      MutableLiveData(savedStateHandle.get(LAST_SEARCH_QUERY) ?: "")

    state = queryLiveData
      .distinctUntilChanged()
      .switchMap { queryString ->
        liveData {
          if (queryString.isNotBlank()) {
            val uiState = repository.getSearchResultStream(queryString)
              .map {
                UiState(
                  query = queryString,
                  searchResult = it
                )
              }
              .asLiveData(Dispatchers.Main)
            emitSource(uiState)
          } else {
            val uiState = repository.getEmptyResult()
              .map {
                UiState(
                  query = "",
                  searchResult = it
                )
              }
              .asLiveData(Dispatchers.Main)
            emitSource(uiState)
          }
        }
      }
    accept = { action ->
      when (action) {
        is Search -> queryLiveData.postValue(action.query)
      }
    }
  }

  override fun onCleared() {
    savedStateHandle[LAST_SEARCH_QUERY] = state.value?.query
    super.onCleared()
  }
}

sealed class UiAction {
  data class Search(val query: String) : UiAction()
}

data class UiState(
  val query: String,
  val searchResult: WeatherSearchResult
)

private const val LAST_SEARCH_QUERY: String = "last_search_query"
