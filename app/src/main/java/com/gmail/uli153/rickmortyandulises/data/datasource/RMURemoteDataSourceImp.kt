package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterIdsResponse
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity
import com.gmail.uli153.rickmortyandulises.data.services.ApiService
import com.gmail.uli153.rickmortyandulises.data.services.GraphQLService
import org.json.JSONObject

class RMURemoteDataSourceImp(
    private val apiService: ApiService,
    private val graphQLService: GraphQLService
): RMURemoteDataSource {

    @Throws
    override suspend fun getCharacterIds(page: Int, name: String, status: String?): CharacterIdsResponse {
        val query = """
                query {
                    characters(page: $page, filter: { name: "$name" }) {
                        info {
                            count
                            pages
                            next
                            prev
                        }
                        results {
                             id
                        }
                    }
                }
            """.trimIndent()
        val paramObject = JSONObject()
        paramObject.put("query", query)
        val response = graphQLService.getCharacterIds(paramObject.toString())
        return response.body()?.data?.characters
            ?: throw Exception("Error fetching character ids")

    }

    override suspend fun getCharacters(ids: List<Long>): List<CharacterEntity> {
        return apiService.getCharacters(ids).body() ?: throw Exception("Error fetching character ids")
    }

    override suspend fun getEpisodesByIds(ids: List<Long>): List<EpisodeEntity> {
        //todo handle error
        return try {
            val response = apiService.getAllEpisodes(ids)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }
}