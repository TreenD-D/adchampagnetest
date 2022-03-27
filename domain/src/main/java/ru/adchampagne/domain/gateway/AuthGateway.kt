package ru.adchampagne.domain.gateway

interface AuthGateway {
    suspend fun getAuthorizationToken(): String
}