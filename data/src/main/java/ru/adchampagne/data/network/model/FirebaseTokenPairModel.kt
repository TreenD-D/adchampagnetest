package ru.adchampagne.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseTokenPairModel(
    @SerialName("oldToken") val oldToken: String,
    @SerialName("newToken") val newToken: String
)