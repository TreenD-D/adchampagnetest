package ru.adchampagne.domain.interactor.auth

import ru.adchampagne.domain.gateway.AuthGateway
import ru.adchampagne.domain.global.DispatcherProvider
import ru.adchampagne.domain.global.UseCaseWithParams

class RecoverUseCase(
    private val authGateway: AuthGateway,
    dispatcherProvider: DispatcherProvider
) : UseCaseWithParams<String, Boolean>(dispatcherProvider.io) {
    override suspend fun execute(parameters: String): Boolean =
        authGateway.recover(parameters)
}