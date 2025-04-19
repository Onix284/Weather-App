package org.example.room_contactpp.Models

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int? = null,
    val gust: Double? = null,
    val speed: Double? = null
)