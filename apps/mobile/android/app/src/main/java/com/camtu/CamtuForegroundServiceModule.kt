package com.camtu

import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.util.UUID

class CamtuForegroundServiceModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private val preferences: SharedPreferences by lazy {
    reactApplicationContext.getSharedPreferences("camtu_preferences", android.content.Context.MODE_PRIVATE)
  }

  override fun getName(): String = "CamtuForegroundService"

  @ReactMethod
  fun startForegroundStreaming(title: String, body: String, promise: Promise) {
    try {
      val intent = Intent(reactApplicationContext, ForegroundStreamingService::class.java).apply {
        action = ForegroundStreamingService.ACTION_START
        putExtra(ForegroundStreamingService.EXTRA_TITLE, title)
        putExtra(ForegroundStreamingService.EXTRA_BODY, body)
      }

      ContextCompat.startForegroundService(reactApplicationContext, intent)
      promise.resolve(true)
    } catch (error: Exception) {
      promise.reject("foreground_start_failed", error)
    }
  }

  @ReactMethod
  fun updateForegroundStreaming(title: String, body: String, promise: Promise) {
    startForegroundStreaming(title, body, promise)
  }

  @ReactMethod
  fun stopForegroundStreaming(promise: Promise) {
    try {
      val intent = Intent(reactApplicationContext, ForegroundStreamingService::class.java).apply {
        action = ForegroundStreamingService.ACTION_STOP
      }

      reactApplicationContext.startService(intent)
      promise.resolve(true)
    } catch (error: Exception) {
      promise.reject("foreground_stop_failed", error)
    }
  }

  @ReactMethod
  fun isForegroundStreamingActive(promise: Promise) {
    promise.resolve(ForegroundStreamingService.isRunning)
  }

  @ReactMethod
  fun getOrCreateDeviceId(promise: Promise) {
    val currentDeviceId = preferences.getString("device_id", null)
    if (currentDeviceId != null) {
      promise.resolve(currentDeviceId)
      return
    }

    val nextDeviceId = "camtu-${UUID.randomUUID()}"
    preferences.edit().putString("device_id", nextDeviceId).apply()
    promise.resolve(nextDeviceId)
  }

  @ReactMethod
  fun getForegroundStreamingPreference(promise: Promise) {
    promise.resolve(preferences.getBoolean("foreground_enabled", true))
  }

  @ReactMethod
  fun setForegroundStreamingPreference(enabled: Boolean, promise: Promise) {
    preferences.edit().putBoolean("foreground_enabled", enabled).apply()
    promise.resolve(true)
  }

  @ReactMethod
  fun getLastPairingPayload(promise: Promise) {
    promise.resolve(preferences.getString("last_pairing_payload", null))
  }

  @ReactMethod
  fun setLastPairingPayload(payload: String, promise: Promise) {
    preferences.edit().putString("last_pairing_payload", payload).apply()
    promise.resolve(true)
  }

  @ReactMethod
  fun clearLastPairingPayload(promise: Promise) {
    preferences.edit().remove("last_pairing_payload").apply()
    promise.resolve(true)
  }
}
