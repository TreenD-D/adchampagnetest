package ru.adchampagne.domain.interactor.notification

import ru.adchampagne.domain.gateway.NotificationGateway
import ru.adchampagne.domain.global.DispatcherProvider
import ru.adchampagne.domain.global.UseCaseWithParams

class RegisterFirebaseTokenUseCase(
    private val notificationGateway: NotificationGateway,
    dispatcherProvider: DispatcherProvider
) : UseCaseWithParams<String, Unit>(dispatcherProvider.io) {
    override suspend fun execute(parameters: String) =
        notificationGateway.registerNotificationToken(parameters)
}