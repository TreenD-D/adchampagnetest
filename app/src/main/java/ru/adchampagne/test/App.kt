package ru.adchampagne.test

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import ru.adchampagne.test.di.appComponent
import ru.adchampagne.test.observer.AppLifecycleObserver
import ru.adchampagne.data.preference.PreferencesWrapper
import com.google.firebase.FirebaseApp
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.yariksoffice.lingver.Lingver
import org.koin.core.annotation.KoinExperimentalAPI

class App : Application() {
    private val appLifecycleObserver: AppLifecycleObserver by inject()
    private val preferences: PreferencesWrapper by inject()

    override fun onCreate() {
        super.onCreate()
        initFirebase()
        initKoin()
        initLifecycleObserver()
        initLanguage()
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    @OptIn(KoinExperimentalAPI::class)
    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            modules(appComponent)
            fragmentFactory()
        }
    }

    private fun initLifecycleObserver() {
        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(appLifecycleObserver)
    }

    private fun initLanguage() {
        Lingver.init(this, preferences.language.get())
    }
}