package com.gmail.uli153.rickmortyandulises.data.services

import com.gmail.uli153.rickmortyandulises.data.dto.CharacterDTO
import com.gmail.uli153.rickmortyandulises.data.dto.EpisodeDTO
import com.gmail.uli153.rickmortyandulises.data.dto.LocationDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character/{ids}")
    suspend fun getCharacters(@Path("ids") ids: List<Long>): Response<List<CharacterDTO>>

    @GET("episode/{ids}")
    suspend fun getAllEpisodes(@Path("ids") ids: List<Long>): Response<List<EpisodeDTO>>

    @GET("location/{ids}")
    suspend fun getAllLocations(@Path("ids") ids: List<Long>): Response<List<LocationDTO>>
}