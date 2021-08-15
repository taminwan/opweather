#include <jni.h>

extern "C" {
    JNIEXPORT jstring JNICALL Java_com_minwan_weatherforecast_loader_OpenWeatherLoader_getOpenWeatherAppId(JNIEnv * env, jobject obj);
}

JNIEXPORT jstring JNICALL
Java_com_minwan_weatherforecast_loader_OpenWeatherLoader_getOpenWeatherAppId(JNIEnv * env, jobject obj)
{
    return (*env).NewStringUTF("60c6fbeb4b93ac653c492ba806fc346d");
}

