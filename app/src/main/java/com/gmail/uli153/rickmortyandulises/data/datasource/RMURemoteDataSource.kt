package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterIdsResponse
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity

interface RMURemoteDataSource {

    suspend fun getCharacterIds(page: Int, name: String, status: String?): CharacterIdsResponse

    suspend fun getCharacters(ids: List<Long>): List<CharacterEntity>

    suspend fun getEpisodesByIds(ids: List<Long>): List<EpisodeEntity>
}