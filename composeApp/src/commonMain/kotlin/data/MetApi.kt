package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import models.ArtifactData

class MetApiService {
    private val ktor = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    private val baseMetUrl = "https://collectionapi.metmuseum.org/public/collection/v1"

    suspend fun getObjectByID(id: String): Result<ArtifactData> {
        return try {
            val responseJson = ktor.get("$baseMetUrl/objects/$id").body<ArtifactData>()
            Result.success(responseJson)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
