LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := openweather-jni
LOCAL_SRC_FILES := openweather-jni.cpp

include $(BUILD_SHARED_LIBRARY)
