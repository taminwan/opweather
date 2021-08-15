package com.minwan.weatherforecast

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class AppInstrumentedTest {
  @Test
  fun checkAppPackageName() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("com.minwan.weatherforecast", appContext.packageName)
  }
}