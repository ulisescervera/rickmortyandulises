package com.gmail.uli153.rickmortyandulises.data

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String,
        @Query("status") status: String?
    ): Response<RemoteResponse<CharacterEntity>>
}