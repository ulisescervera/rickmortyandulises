package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.ResourceIdsResponse
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeIdsRemoteResponse
import com.gmail.uli153.rickmortyandulises.data.services.ApiService
import com.gmail.uli153.rickmortyandulises.data.services.GraphQLService
import org.json.JSONObject

class RMURemoteDataSourceImp(
    private val apiService: ApiService,
    private val graphQLService: GraphQLService
): RMURemoteDataSource {

    @Throws
    override suspend fun getCharacterIds(page: Int, name: String, status: String?): ResourceIdsResponse {
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

    override suspend fun getEpisodeIds(page: Int): ResourceIdsResponse {
        val query = """
                query {
                    episodes(page: $page) {
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
        val response = graphQLService.getEpisodeIds(paramObject.toString())
        return response.body()?.data?.episodes
            ?: throw Exception("Error fetching episode ids")
    }

    override suspend fun getEpisodes(ids: List<Long>): List<EpisodeEntity> {
        return apiService.getAllEpisodes(ids).body() ?: throw Exception("Error fetching episodes ids")
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