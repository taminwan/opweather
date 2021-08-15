package com.minwan.weatherforecast.loader;

public class OpenWeatherLoader {

  public static void init() {
    System.loadLibrary("openweather-jni");
  }

  public static native String getOpenWeatherAppId();
}
