package ru.adchampagne.test.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import ru.adchampagne.test.feature.AppActivity
import ru.adchampagne.device.manager.NOTIFICATION_PARAM_METADATA
import ru.adchampagne.device.manager.NOTIFICATION_PARAM_TYPE

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val activityIntent = Intent(context, AppActivity::class.java).apply {
            putExtras(
                bundleOf(
                    NOTIFICATION_PARAM_TYPE to intent.getStringExtra(NOTIFICATION_PARAM_TYPE),
                    NOTIFICATION_PARAM_METADATA to intent.getStringExtra(NOTIFICATION_PARAM_METADATA)
                )
            )
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(activityIntent)
    }
}
