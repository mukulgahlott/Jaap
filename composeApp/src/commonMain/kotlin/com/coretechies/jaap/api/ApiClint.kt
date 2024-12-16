package com.coretechies.jaap.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {

    private const val BASE_URL = "http://192.168.1.19:8081/jaap/"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                prettyPrint = true
                isLenient = true
            })
        }
        // Default request configuration
        defaultRequest {
            url(BASE_URL)
            header("Authorization", "Bearer <YOUR-TOKEN>")
            header("Content-Type", "application/json")
        }

    }
}
