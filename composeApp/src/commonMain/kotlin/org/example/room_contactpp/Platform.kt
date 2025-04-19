package org.example.room_contactpp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform