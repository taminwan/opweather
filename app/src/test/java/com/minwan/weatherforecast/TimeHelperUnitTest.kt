package com.minwan.weatherforecast

import com.minwan.weatherforecast.helper.TimeHelper
import org.junit.*

class TimeHelperUnitTest {
  @Test
  fun convertEpochTimeToDisplayText_isCorrect() {
    val epochTime = 1629028800L
    val displayTime = TimeHelper.convertEpochTimeToDisplayText(epochTime)
    Assert.assertEquals("Sun, 15 Aug 2021", displayTime)
  }

  @Test
  fun convertEpochTimeToDisplayText_withZero_isFailed() {
    val epochTime = 0L
    val displayTime = TimeHelper.convertEpochTimeToDisplayText(epochTime)
    Assert.assertNull(displayTime)
  }

  @Test
  fun convertEpochTimeToDisplayText_withNegativeNumber_isFailed() {
    val epochTime = -1L
    val displayTime = TimeHelper.convertEpochTimeToDisplayText(epochTime)
    Assert.assertNull(displayTime)
  }

  @Test
  fun convertEpochTimeToDisplayText_withEnormousTimeNumber_isFailed() {
    val epochTime = 1629028800000L
    val displayTime = TimeHelper.convertEpochTimeToDisplayText(epochTime)
    Assert.assertEquals("Thu, 14 Nov 53591", displayTime)
  }
}