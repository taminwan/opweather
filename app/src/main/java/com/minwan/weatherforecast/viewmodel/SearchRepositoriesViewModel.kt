package com.minwan.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.minwan.weatherforecast.helper.Cons
import com.minwan.weatherforecast.helper.UiErrorCode
import com.minwan.weatherforecast.helper.UiErrorCode.SHORT_LOCATION_SEARCH
import com.minwan.weatherforecast.helper.UiErrorCode.NO_ERROR
import com.minwan.weatherforecast.model.WeatherSearchResult
import com.minwan.weatherforecast.repo.OpenWeatherRepository
import com.minwan.weatherforecast.viewmodel.UiAction.Search
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlin.String as ring1

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
    val queryLiveData = MutableLiveData(savedStateHandle.get(LAST_SEARCH_QUERY) ?: "")

    state = queryLiveData
      .distinctUntilChanged()
      .switchMap { queryString ->
        liveData {
          if (queryString.isNotBlank() &&
            queryString.length >= Cons.MIN_LENGTH_REGION_NAME
          ) {
            val uiState = repository.getSearchResultStream(queryString)
              .map {
                UiState(
                  query = queryString,
                  searchResult = it,
                )
              }
              .asLiveData(Dispatchers.Main)
            emitSource(uiState)
          } else {
            if (queryString.isNotEmpty()) {
              val uiState = repository.getEmptyResultError(SHORT_LOCATION_SEARCH.errorMessage)
                .map {
                  UiState(
                    query = queryString,
                    searchResult = it,
                    errorCode = SHORT_LOCATION_SEARCH
                  )
                }
                .asLiveData(Dispatchers.Main)
              emitSource(uiState)
            }
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
  data class Search(val query: ring1) : UiAction()
}

data class UiState(
  val query: ring1,
  val searchResult: WeatherSearchResult,
  var errorCode: UiErrorCode = NO_ERROR,
)

private const val LAST_SEARCH_QUERY: ring1 = "last_search_query"
