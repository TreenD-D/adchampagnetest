package ru.adchampagne.data.gateway

import ru.adchampagne.data.network.CommonApi
import ru.adchampagne.domain.gateway.AuthGateway

class AuthGatewayImpl(
    private val commonApi: CommonApi
) : AuthGateway {
    override suspend fun getAuthorizationToken(): String {
        commonApi.getSampleData(0.0).lat.toString()
        TODO("STUB")
    }
}