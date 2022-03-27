package ru.adchampagne.domain.interactor.auth

import ru.adchampagne.domain.gateway.AuthGateway
import ru.adchampagne.domain.global.DispatcherProvider
import ru.adchampagne.domain.global.UseCase

class LogoutUseCase(
    private val authGateway: AuthGateway,
    dispatcherProvider: DispatcherProvider
) : UseCase<Unit>(dispatcherProvider.io) {
    override suspend fun execute() = authGateway.logout()
}