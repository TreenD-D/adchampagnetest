package ru.adchampagne.device.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.koin.android.ext.android.inject
import pro.appcraft.either.AsyncEither
import pro.appcraft.either.flatMap
import ru.adchampagne.device.manager.NotificationMessageManager
import ru.adchampagne.domain.global.CoroutineProvider
import ru.adchampagne.domain.interactor.auth.GetAuthorizationTokenUseCase
import ru.adchampagne.domain.interactor.notification.RegisterFirebaseTokenUseCase

private const val ID = "uuid"
private const val TITLE = "title"
private const val BODY = "body"
private const val TYPE = "notificationType"
private const val METADATA = "metadata"

private val logger = KotlinLogging.logger {}

class AppFirebaseMessagingService : FirebaseMessagingService() {
    private val manager: NotificationMessageManager by inject()
    private val coroutineProvider: CoroutineProvider by inject()
    private val registerFirebaseTokenUseCase: RegisterFirebaseTokenUseCase by inject()
    private val getAuthorizationTokenUseCase: GetAuthorizationTokenUseCase by inject()

    override fun onNewToken(newToken: String) {
        coroutineProvider.scopeIo.launch {
            getAuthorizationTokenUseCase()
                .flatMap { token ->
                    if (token.isNotBlank())
                        registerFirebaseTokenUseCase(newToken)
                    else AsyncEither.Right(Unit)
                }
                .fold({}) { logger.error { it.message.orEmpty() } }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val title = if (data.containsKey(TITLE)) data[TITLE].orEmpty() else ""
        val body = if (data.containsKey(BODY)) data[BODY].orEmpty() else ""
        val type = if (data.containsKey(TYPE)) data[TYPE] else null
        val metadata = if (data.containsKey(METADATA)) data[METADATA] else null
        val id = if (data.containsKey(ID)) data[ID] else null

        manager.showMessageWithAction(
            title = title,
            body = body,
            type = type,
            data = metadata,
            id = id
        )
    }
}
