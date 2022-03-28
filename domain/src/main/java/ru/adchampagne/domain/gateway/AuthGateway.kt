package ru.adchampagne.domain.gateway

import kotlinx.coroutines.flow.Flow
import ru.adchampagne.domain.model.auth.AuthParams
import ru.adchampagne.domain.model.auth.AuthState

interface AuthGateway {
    fun observeAuthState(): Flow<AuthState>

    suspend fun getAuthorizationToken(): String
    suspend fun logout()

    suspend fun login(auth: AuthParams): String?
    suspend fun recover(mail: String): Boolean
    suspend fun register(auth: AuthParams): Long?
    suspend fun getCurrentUserMail(): String?
}