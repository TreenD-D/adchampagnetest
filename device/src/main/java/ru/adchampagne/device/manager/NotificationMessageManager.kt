package ru.adchampagne.device.manager

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.adchampagne.device.R

private const val NOTIFICATION_ID = 100
private const val NOTIFICATION_CHANNEL_ID = "adchampagnetestNotifications"
private const val NOTIFICATION_CHANNEL_NAME = "adchampagnetest Notifications"
private const val REQUEST_CODE = 1

const val NOTIFICATION_PARAM_TYPE = "type"
const val NOTIFICATION_PARAM_METADATA = "metadata"
const val NOTIFICATION_PARAM_ID = "id"
const val NOTIFICATION_BROADCAST_ACTION = "ru.adchampagne.test.notification"

class NotificationMessageManager(private val context: Context) {
    private val notificationManager: NotificationManager
        get() = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val notificationFlags: Int =
        (Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_SOUND)

    init {
        initChannels()
    }

    private fun initChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createIfNotExistNotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createIfNotExistNotificationChannel(channelId: String, channelName: String) {
        notificationManager.getNotificationChannel(channelId) ?: NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build()
            )
            enableLights(true)
            enableVibration(true)
        }.also { notificationManager.createNotificationChannel(it) }
    }

    internal fun showMessageWithAction(
        title: String,
        body: String,
        type: String?,
        data: String?,
        id: String?
    ) {
        val intent = createPushIntent(context, type, data, id)
        val notification = createNotification(NOTIFICATION_CHANNEL_ID, title, body, intent)
        showNotification(notification)
    }

    fun showMessage(title: String, body: String) {
        val notification = createNotification(NOTIFICATION_CHANNEL_ID, title, body, null)
        showNotification(notification)
    }

    private fun createPushIntent(
        context: Context,
        type: String?,
        data: String?,
        id: String?
    ): PendingIntent {
        val intent = Intent(NOTIFICATION_BROADCAST_ACTION).apply {
            putExtra(NOTIFICATION_PARAM_TYPE, type)
            putExtra(NOTIFICATION_PARAM_METADATA, data)
            putExtra(NOTIFICATION_PARAM_ID, id)
        }
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotification(
        channelId: String,
        title: String,
        body: String,
        intent: PendingIntent?
    ): Notification {
        return NotificationCompat.Builder(context, channelId)
            // .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(intent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentText(body)
            .setColor(ContextCompat.getColor(context, R.color.appPrimary))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    private fun showNotification(notification: Notification) =
        notificationManager.notify(NOTIFICATION_ID, notification)
}
