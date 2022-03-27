package ru.adchampagne.domain.gateway

interface NotificationGateway {
    suspend fun registerNotificationToken(newToken: String)
}