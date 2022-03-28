package ru.adchampagne.data.gateway

import kotlinx.coroutines.flow.Flow
import ru.adchampagne.data.preference.PreferencesWrapper
import ru.adchampagne.data.storage.dao.UserDao
import ru.adchampagne.data.storage.entity.UserEntity
import ru.adchampagne.domain.gateway.AuthGateway
import ru.adchampagne.domain.model.auth.AuthParams
import ru.adchampagne.domain.model.auth.AuthState

class AuthGatewayImpl(
    private val usersDao: UserDao,
    private val prefs: PreferencesWrapper
) : AuthGateway {
    override suspend fun getAuthorizationToken(): String {
        "smth"
        TODO("STUB")
    }

    override fun observeAuthState(): Flow<AuthState> = prefs.authStateFlow

    override suspend fun logout() {
        prefs.drop()
    }

    override suspend fun getCurrentUserMail() =
        prefs.authToken.get()


    override suspend fun login(auth: AuthParams): String? {
        val user = usersDao.getEntityByMail(auth.username)
        return if (user?.password == auth.password) {
            prefs.authToken.setAndCommit(user.mail)
            user.mail
        } else null
    }

    override suspend fun recover(mail: String): Boolean {
        return usersDao.getEntityByMail(mail) != null
    }

    override suspend fun register(auth: AuthParams): Long? {
        return if (usersDao.getEntityByMail(auth.username) == null) {
            val user = UserEntity(
                mail = auth.username,
                password = auth.password
            )
            usersDao.add(user).also {
                prefs.authToken.setAndCommit(user.mail)
            }
        } else null
    }
}