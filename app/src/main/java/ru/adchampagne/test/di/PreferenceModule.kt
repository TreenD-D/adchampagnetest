package ru.adchampagne.test.di

import androidx.preference.PreferenceManager
import ru.adchampagne.data.preference.PreferencesWrapper
import com.tfcporciuncula.flow.FlowSharedPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
internal val preferenceModule = module {
    single { PreferencesWrapper(get()) }
    single { FlowSharedPreferences(get()) }
    factory { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
}