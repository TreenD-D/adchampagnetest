package ru.adchampagne.test.di

import ru.adchampagne.domain.interactor.auth.GetAuthorizationTokenUseCase
import ru.adchampagne.domain.interactor.notification.RegisterFirebaseTokenUseCase
import org.koin.dsl.module

internal val interactorModule = module {
    single { GetAuthorizationTokenUseCase(get(), get()) }

    single { RegisterFirebaseTokenUseCase(get(), get()) }
}