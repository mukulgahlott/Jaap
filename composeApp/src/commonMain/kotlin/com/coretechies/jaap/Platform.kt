package com.coretechies.jaap

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform