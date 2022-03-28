package ru.adchampagne.domain.interactor.auth

import ru.adchampagne.domain.gateway.AuthGateway
import ru.adchampagne.domain.global.DispatcherProvider
import ru.adchampagne.domain.global.UseCaseWithParams
import ru.adchampagne.domain.model.auth.AuthParams

class LoginUseCase(
    private val authGateway: AuthGateway,
    dispatcherProvider: DispatcherProvider
) : UseCaseWithParams<AuthParams, String?>(dispatcherProvider.io) {
    override suspend fun execute(parameters: AuthParams): String? =
        authGateway.login(parameters)
}