package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity
import com.gmail.uli153.rickmortyandulises.data.entities.LocationEntity

interface RMULocalDataSource {

    suspend fun getCharacter(id: Long): CharacterEntity?

    suspend fun getCharacters(ids: List<Long>): List<CharacterEntity>

    suspend fun insertCharacters(characters: List<CharacterEntity>)

    suspend fun getEpisodes(ids: List<Long>): List<EpisodeEntity>

    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    suspend fun getLocations(ids: List<Long>): List<LocationEntity>

    suspend fun insertLocations(locations: List<LocationEntity>)

}