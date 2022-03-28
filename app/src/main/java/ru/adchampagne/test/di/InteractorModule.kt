package ru.adchampagne.test.di

import org.koin.dsl.module
import ru.adchampagne.domain.interactor.auth.GetAuthorizationTokenUseCase
import ru.adchampagne.domain.interactor.auth.GetCurrentUserMailUseCase
import ru.adchampagne.domain.interactor.auth.LoginUseCase
import ru.adchampagne.domain.interactor.auth.LogoutUseCase
import ru.adchampagne.domain.interactor.auth.ObserveAuthStateUseCase
import ru.adchampagne.domain.interactor.auth.RecoverUseCase
import ru.adchampagne.domain.interactor.auth.RegisterUseCase
import ru.adchampagne.domain.interactor.notification.RegisterFirebaseTokenUseCase

internal val interactorModule = module {
    single { RegisterFirebaseTokenUseCase(get(), get()) }
    single { GetAuthorizationTokenUseCase(get(), get()) }

    single { ObserveAuthStateUseCase(get(), get()) }
    single { LogoutUseCase(get(), get()) }
    single { LoginUseCase(get(), get()) }
    single { RecoverUseCase(get(), get()) }
    single { RegisterUseCase(get(), get()) }
    single {GetCurrentUserMailUseCase(get(), get())}
}