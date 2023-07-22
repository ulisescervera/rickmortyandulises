package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.dto.CharacterDTO
import com.gmail.uli153.rickmortyandulises.data.dto.EpisodeDTO
import com.gmail.uli153.rickmortyandulises.data.dto.LocationDTO
import com.gmail.uli153.rickmortyandulises.data.entities.ResourceIdsResponse

interface RMURemoteDataSource {

    suspend fun getCharacterIds(page: Int, name: String, status: String?): ResourceIdsResponse

    suspend fun getCharacters(ids: List<Long>): List<CharacterDTO>

    suspend fun getEpisodeIds(page: Int): ResourceIdsResponse

    suspend fun getEpisodes(ids: List<Long>): List<EpisodeDTO>

    suspend fun getEpisodesByIds(ids: List<Long>): List<EpisodeDTO>

    suspend fun getLocationIds(page: Int): ResourceIdsResponse

    suspend fun getLocations(ids: List<Long>): List<LocationDTO>
}