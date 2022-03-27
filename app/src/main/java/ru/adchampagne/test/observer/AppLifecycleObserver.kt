package ru.adchampagne.test.observer

import android.content.Context
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import ru.adchampagne.test.receiver.NotificationReceiver
import ru.adchampagne.device.manager.NOTIFICATION_BROADCAST_ACTION

class AppLifecycleObserver(private val context: Context) : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            /**
             * When app is started
             */
            Lifecycle.Event.ON_CREATE -> {
                context.registerReceiver(
                    NotificationReceiver(),
                    IntentFilter(NOTIFICATION_BROADCAST_ACTION)
                )
            }

            /**
             * When app enters foreground
             */
            Lifecycle.Event.ON_START -> {
            }

            /**
             * When app enters background
             */
            Lifecycle.Event.ON_STOP -> {
            }

            /**
             * When app surely enters foreground
             */
            Lifecycle.Event.ON_RESUME -> {
            }

            else -> {
            }
        }
    }
}