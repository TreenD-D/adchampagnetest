package ru.adchampagne.data.gateway

import ru.adchampagne.data.network.AuthApi
import ru.adchampagne.data.network.model.FirebaseTokenPairModel
import ru.adchampagne.data.preference.PreferencesWrapper
import ru.adchampagne.domain.gateway.NotificationGateway

class NotificationGatewayImpl(
    private val authApi: AuthApi,
    private val preferences: PreferencesWrapper
) : NotificationGateway {
    override suspend fun registerNotificationToken(newToken: String) {
        val tokenPair = FirebaseTokenPairModel(
            oldToken = preferences.firebaseToken.get(),
            newToken = newToken
        )
        preferences.firebaseToken.set(newToken)

        TODO("STUB")
    }
}