package com.gmail.uli153.rickmortyandulises.data.services

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterIdsRemoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GraphQLService {

    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun getCharacterIds(@Body graphQL: String): Response<CharacterIdsRemoteResponse>
}