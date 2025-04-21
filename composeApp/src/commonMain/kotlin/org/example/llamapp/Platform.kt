package org.example.llamapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform