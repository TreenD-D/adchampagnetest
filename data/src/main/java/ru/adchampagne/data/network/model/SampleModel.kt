package ru.adchampagne.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SampleModel(
    @SerialName("lon") val lng: Double,
    @SerialName("lat") val lat: Double
)