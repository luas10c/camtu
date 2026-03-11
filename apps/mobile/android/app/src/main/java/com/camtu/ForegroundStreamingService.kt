package com.camtu

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat

class ForegroundStreamingService : Service() {

  companion object {
    const val CHANNEL_ID = "camtu_streaming"
    const val NOTIFICATION_ID = 4107
    const val ACTION_START = "com.camtu.action.START_FOREGROUND_STREAM"
    const val ACTION_STOP = "com.camtu.action.STOP_FOREGROUND_STREAM"
    const val EXTRA_TITLE = "extra_title"
    const val EXTRA_BODY = "extra_body"

    @Volatile
    var isRunning: Boolean = false
  }

  private var wakeLock: PowerManager.WakeLock? = null
  private var wifiLock: WifiManager.WifiLock? = null

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    when (intent?.action) {
      ACTION_STOP -> {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        return START_NOT_STICKY
      }
      else -> {
        val title = intent?.getStringExtra(EXTRA_TITLE) ?: "Camtu transmitindo"
        val body = intent?.getStringExtra(EXTRA_BODY) ?: "Camera em foreground para o desktop"

        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification(title, body))
        acquireWakeLock()
        acquireWifiLock()
        isRunning = true
        return START_STICKY
      }
    }
  }

  override fun onDestroy() {
    releaseWakeLock()
    releaseWifiLock()
    isRunning = false
    super.onDestroy()
  }

  private fun buildNotification(title: String, body: String) =
    NotificationCompat.Builder(this, CHANNEL_ID)
      .setContentTitle(title)
      .setContentText(body)
      .setSmallIcon(R.mipmap.ic_launcher)
      .setOngoing(true)
      .setOnlyAlertOnce(true)
      .setPriority(NotificationCompat.PRIORITY_LOW)
      .setContentIntent(
        PendingIntent.getActivity(
          this,
          0,
          Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
          },
          PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
      )
      .build()

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      return
    }

    val notificationManager =
      getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channel = NotificationChannel(
      CHANNEL_ID,
      "Camtu Streaming",
      NotificationManager.IMPORTANCE_LOW
    ).apply {
      description = "Mantem a camera transmitindo em foreground para o desktop."
    }

    notificationManager.createNotificationChannel(channel)
  }

  private fun acquireWakeLock() {
    if (wakeLock?.isHeld == true) {
      return
    }

    val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
    wakeLock = powerManager.newWakeLock(
      PowerManager.PARTIAL_WAKE_LOCK,
      "camtu:foreground-stream"
    ).apply {
      setReferenceCounted(false)
      acquire(10 * 60 * 1000L)
    }
  }

  private fun releaseWakeLock() {
    wakeLock?.let { currentWakeLock ->
      if (currentWakeLock.isHeld) {
        currentWakeLock.release()
      }
    }
    wakeLock = null
  }

  private fun acquireWifiLock() {
    if (wifiLock?.isHeld == true) {
      return
    }

    val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager ?: return
    wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "camtu:foreground-stream").apply {
      setReferenceCounted(false)
      acquire()
    }
  }

  private fun releaseWifiLock() {
    wifiLock?.let { currentWifiLock ->
      if (currentWifiLock.isHeld) {
        currentWifiLock.release()
      }
    }
    wifiLock = null
  }
}
