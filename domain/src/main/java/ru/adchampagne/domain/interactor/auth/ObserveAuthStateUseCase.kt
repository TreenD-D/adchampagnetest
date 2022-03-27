package ru.adchampagne.domain.interactor.auth

import kotlinx.coroutines.flow.Flow
import ru.adchampagne.domain.gateway.AuthGateway
import ru.adchampagne.domain.global.DispatcherProvider
import ru.adchampagne.domain.global.FlowUseCase
import ru.adchampagne.domain.model.auth.AuthState

class ObserveAuthStateUseCase(
    private val authGateway: AuthGateway,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<AuthState>(dispatcherProvider.io) {
    override fun execute(): Flow<AuthState> = authGateway.observeAuthState()
}