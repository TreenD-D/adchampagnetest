package ru.adchampagne.test.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.adchampagne.test.feature.AppViewModel
import ru.adchampagne.test.feature.auth.AuthViewModel
import ru.adchampagne.test.feature.profile.ProfileViewModel
import ru.adchampagne.test.feature.splash.SplashViewModel
import ru.adchampagne.test.global.viewmodel.NavigationViewModel

internal val viewModelModule = module {
    viewModel { NavigationViewModel(get()) }
    viewModel { AppViewModel(get()) }

    viewModel { SplashViewModel(get()) }

    viewModel { AuthViewModel(get(), get(), get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get()) }
}