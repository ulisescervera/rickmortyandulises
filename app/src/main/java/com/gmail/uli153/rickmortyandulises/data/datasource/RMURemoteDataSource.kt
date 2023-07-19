package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.ResourceIdsResponse
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity

interface RMURemoteDataSource {

    suspend fun getCharacterIds(page: Int, name: String, status: String?): ResourceIdsResponse

    suspend fun getCharacters(ids: List<Long>): List<CharacterEntity>

    suspend fun getEpisodeIds(page: Int): ResourceIdsResponse

    suspend fun getEpisodes(ids: List<Long>): List<EpisodeEntity>

    suspend fun getEpisodesByIds(ids: List<Long>): List<EpisodeEntity>
}