package data

import models.ArtifactData

class MetRepository {
    private val artifactsCache: MutableMap<String, ArtifactData> = mutableMapOf()

    private val metApiService = MetApiService()

    suspend fun getArtifactById(id: String): Result<ArtifactData?> {
        if (artifactsCache.containsKey(id)) return Result.success(artifactsCache[id])
        val result = metApiService.getObjectByID(id)
        if (!result.isSuccess || result.getOrNull() == null) return result
        val artifact = result.getOrThrow()
        artifactsCache[id] = artifact
        return Result.success(artifact)
    }
}