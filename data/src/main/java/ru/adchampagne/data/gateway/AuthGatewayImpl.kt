package ru.adchampagne.data.gateway

import kotlinx.coroutines.flow.Flow
import ru.adchampagne.data.preference.PreferencesWrapper
import ru.adchampagne.data.storage.dao.UserDao
import ru.adchampagne.domain.gateway.AuthGateway
import ru.adchampagne.domain.model.auth.AuthState

class AuthGatewayImpl(
    private val usersDao: UserDao,
    private val prefs: PreferencesWrapper
) : AuthGateway {
    /*    override suspend fun getAuthorizationToken(): String {
        commonApi.getSampleData(0.0).lat.toString()
        TODO("STUB")
    }*/
    override fun observeAuthState(): Flow<AuthState> = prefs.authStateFlow

    override suspend fun logout() {
        prefs.drop()
    }
}