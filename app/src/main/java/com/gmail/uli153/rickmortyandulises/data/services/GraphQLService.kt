package com.gmail.uli153.rickmortyandulises.data.services

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterIdsRemoteResponse
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeIdsRemoteResponse
import com.gmail.uli153.rickmortyandulises.data.entities.LocationIdsRemoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GraphQLService {

    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun getCharacterIds(@Body graphQL: String): Response<CharacterIdsRemoteResponse>

    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun getEpisodeIds(@Body graphQL: String): Response<EpisodeIdsRemoteResponse>

    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun getLocationIds(@Body graphQL: String): Response<LocationIdsRemoteResponse>
}