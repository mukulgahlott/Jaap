package com.coretechies.jaap.api
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable

class ApiService(private val client: HttpClient) {
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    suspend fun checkIsVerified(
        userName: String,
        passcode: Int,
        deviceId: String,
        fcmToken: String
    ): String {
        return client.get("$baseUrl/checkIsVaried") {
            url {
                parameters.append("userName", userName)
                parameters.append("passcode", passcode.toString())
                parameters.append("deviceId", deviceId)
                parameters.append("fcmToken", fcmToken)
            }
        }.body()
    }

    // Example: POST request to create a new post
    suspend fun createPost(post: Post): Post {
        val response: HttpResponse = client.post("$baseUrl/posts") {
            setBody(post)
        }
        return response.body()
    }
}

data class Post(
    val id: Int = 0,
    val title: String,
    val body: String,
    val userId: Int
)
