package com.gmail.uli153.rickmortyandulises.data.services

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character/{ids}")
    suspend fun getCharacters(@Path("ids") ids: List<Long>): Response<List<CharacterEntity>>

    @GET("episode/{ids}")
    suspend fun getAllEpisodes(@Path("ids") ids: List<Long>): Response<List<EpisodeEntity>>
}