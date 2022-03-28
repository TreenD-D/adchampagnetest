package ru.adchampagne.test.di

import ru.adchampagne.data.gateway.AuthGatewayImpl
import ru.adchampagne.data.gateway.NotificationGatewayImpl
import ru.adchampagne.domain.gateway.AuthGateway
import ru.adchampagne.domain.gateway.NotificationGateway
import org.koin.dsl.module

internal val gatewayModule = module {
    single<AuthGateway> { AuthGatewayImpl(get(), get()) }

    single<NotificationGateway> { NotificationGatewayImpl(get(), get()) }
}