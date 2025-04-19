package org.example.room_contactpp.Models

import kotlinx.serialization.Serializable as Serializable1

@Serializable1
data class Main(
    val feels_like: Double? = null,
    val grnd_level: Int? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
    val sea_level: Int? = null,
    val temp: Double? = null,
    val temp_max: Double? = null,
    val temp_min: Double? = null
)