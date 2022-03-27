package ru.adchampagne.domain.gateway

import kotlinx.coroutines.flow.Flow
import ru.adchampagne.domain.model.auth.AuthState

interface AuthGateway {
    fun observeAuthState(): Flow<AuthState>

    suspend fun logout()

    // suspend fun getAuthorizationToken(): String
}