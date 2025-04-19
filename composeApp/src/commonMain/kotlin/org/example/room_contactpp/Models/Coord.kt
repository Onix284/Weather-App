package org.example.room_contactpp.Models

import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    val lat: Double? = null,
    val lon: Double? = null
)