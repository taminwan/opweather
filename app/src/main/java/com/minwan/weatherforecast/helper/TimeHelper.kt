package com.minwan.weatherforecast.helper

import android.annotation.SuppressLint
import android.util.Log
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant

object TimeHelper {

  const val TAG = "TimeHelper"

  @SuppressLint("SimpleDateFormat")
  private val displayFormat = SimpleDateFormat("EEE, dd MMM yyyy")

  fun convertEpochTimeToDisplayText(epochSeconds: Long): String? {
    if (epochSeconds > 0) {
      try {
        return displayFormat.format(epochSeconds * 1000)
      } catch (e: Exception) {
        Log.e(TAG, e.toString())
      }
    }
    return null
  }
}