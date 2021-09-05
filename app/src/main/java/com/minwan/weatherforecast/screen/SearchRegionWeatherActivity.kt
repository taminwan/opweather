package com.minwan.weatherforecast.screen

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.recyclerview.widget.DividerItemDecoration
import com.minwan.weatherforecast.R
import com.minwan.weatherforecast.databinding.ActivitySearchRegionWeatherBinding
import com.minwan.weatherforecast.helper.Cons
import com.minwan.weatherforecast.helper.Injection
import com.minwan.weatherforecast.model.WeatherSearchResult
import com.minwan.weatherforecast.ui.adapter.WeatherForecastAdapter
import com.minwan.weatherforecast.viewmodel.SearchRepositoriesViewModel
import com.minwan.weatherforecast.viewmodel.UiAction
import com.minwan.weatherforecast.viewmodel.UiState

class SearchRegionWeatherActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivitySearchRegionWeatherBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    // get the view model
    val viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(owner = this))
      .get(SearchRepositoriesViewModel::class.java)

    // add dividers between RecyclerView's row items
    val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    binding.list.addItemDecoration(decoration)

    // bind the state
    binding.bindState(
      uiState = viewModel.state,
      uiActions = viewModel.accept
    )
  }

  /**
   * Binds the [UiState] provided  by the [SearchRepositoriesViewModel] to the UI,
   * and allows the UI to feed back user actions to it.
   */
  private fun ActivitySearchRegionWeatherBinding.bindState(
    uiState: LiveData<UiState>,
    uiActions: (UiAction) -> Unit
  ) {
    val weatherAdapter = WeatherForecastAdapter()
    list.adapter = weatherAdapter
    bindSearch(
      uiState = uiState,
      onQueryChanged = uiActions
    )
    bindList(
      repoAdapter = weatherAdapter,
      uiState = uiState
    )
  }

  private fun ActivitySearchRegionWeatherBinding.bindSearch(
    uiState: LiveData<UiState>,
    onQueryChanged: (UiAction.Search) -> Unit
  ) {
    edtSearch.setOnEditorActionListener { _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_GO) {
        updateWeatherListFromInput(onQueryChanged)
        true
      } else {
        false
      }
    }
    edtSearch.setOnKeyListener { _, keyCode, event ->
      if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
        updateWeatherListFromInput(onQueryChanged)
        true
      } else {
        false
      }
    }

    btnGet.setOnClickListener {
      updateWeatherListFromInput(onQueryChanged)
    }

    uiState
      .map(UiState::query)
      .distinctUntilChanged()
      .observe(this@SearchRegionWeatherActivity, edtSearch::setText)
  }

  private fun ActivitySearchRegionWeatherBinding.updateWeatherListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
    edtSearch.text.trim().let {
      if (it.isNotBlank() && it.length >= Cons.MIN_LENGTH_REGION_NAME) {
        list.scrollToPosition(0)
        onQueryChanged(UiAction.Search(query = it.toString()))
      } else {
        showEmptyList()
        Toast.makeText(
          this@SearchRegionWeatherActivity,
          getString(R.string.search_require_min_search_length, Cons.MIN_LENGTH_REGION_NAME.toString()),
          Toast.LENGTH_LONG
        ).show()
      }
    }
  }

  private fun ActivitySearchRegionWeatherBinding.bindList(
    repoAdapter: WeatherForecastAdapter,
    uiState: LiveData<UiState>,
  ) {
    uiState
      .map(UiState::searchResult)
      .distinctUntilChanged()
      .observe(this@SearchRegionWeatherActivity) { result ->
        when (result) {
          is WeatherSearchResult.Success -> {
            showEmptyList(result.list.isEmpty())
            repoAdapter.submitList(result.list)
          }
          is WeatherSearchResult.Error -> {
            showEmptyList()
            Toast.makeText(
              this@SearchRegionWeatherActivity,
              String.format(getString(R.string.error), result.error.message),
              Toast.LENGTH_LONG
            ).show()
          }
        }
      }
  }

  private fun ActivitySearchRegionWeatherBinding.showEmptyList(show: Boolean = true) {
    emptyList.isVisible = show
    list.isVisible = !show
  }
}