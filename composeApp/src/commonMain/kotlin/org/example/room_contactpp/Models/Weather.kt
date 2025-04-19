package org.example.room_contactpp.Models

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val description: String? = null,
    val icon: String? = null,
)