package ru.adchampagne.domain.interactor.auth

import ru.adchampagne.domain.gateway.AuthGateway
import ru.adchampagne.domain.global.DispatcherProvider
import ru.adchampagne.domain.global.UseCaseWithParams
import ru.adchampagne.domain.model.auth.AuthParams

class RegisterUseCase(
    private val authGateway: AuthGateway,
    dispatcherProvider: DispatcherProvider
) : UseCaseWithParams<AuthParams, Long?>(dispatcherProvider.io) {
    override suspend fun execute(parameters: AuthParams): Long? =
        authGateway.register(parameters)
}