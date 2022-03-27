package ru.adchampagne.test.di

import ru.adchampagne.test.global.dispatcher.error.ErrorHandler
import ru.adchampagne.test.global.dispatcher.event.EventDispatcher
import ru.adchampagne.test.global.dispatcher.notifier.Notifier
import ru.adchampagne.test.observer.AppLifecycleObserver
import ru.adchampagne.device.manager.NotificationMessageManager
import ru.adchampagne.domain.global.CoroutineProvider
import ru.adchampagne.domain.global.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pro.appcraft.lib.permissions.StoragePermissionHandler

internal val appModule = module {
    factory { androidContext().resources }

    factory { AppLifecycleObserver(get()) }

    single { Notifier() }
    single { ErrorHandler(get(), get()) }
    single { EventDispatcher() }
    single { NotificationMessageManager(androidContext()) }
    single { StoragePermissionHandler(androidContext()) }

    single<CoroutineProvider> {
        object : CoroutineProvider {
            override val scopeIo
                get() = CoroutineScope(Dispatchers.IO)
            override val scopeMain
                get() = MainScope()
            override val scopeMainImmediate
                get() = CoroutineScope(Dispatchers.Main.immediate)
            override val scopeUnconfined
                get() = CoroutineScope(Dispatchers.Unconfined)
        }
    }

    single<DispatcherProvider> {
        object : DispatcherProvider {
            override val io: CoroutineDispatcher = Dispatchers.IO
            override val default = Dispatchers.Default
            override val ui = Dispatchers.Main
            override val unconfined = Dispatchers.Unconfined
        }
    }
}