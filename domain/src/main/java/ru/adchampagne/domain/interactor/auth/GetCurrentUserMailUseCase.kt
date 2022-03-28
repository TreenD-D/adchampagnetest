package ru.adchampagne.domain.interactor.auth

import ru.adchampagne.domain.gateway.AuthGateway
import ru.adchampagne.domain.global.DispatcherProvider
import ru.adchampagne.domain.global.UseCase

class GetCurrentUserMailUseCase(
    private val authGateway: AuthGateway,
    dispatcherProvider: DispatcherProvider
) : UseCase<String?>(dispatcherProvider.io) {
    override suspend fun execute() = authGateway.getCurrentUserMail()
}