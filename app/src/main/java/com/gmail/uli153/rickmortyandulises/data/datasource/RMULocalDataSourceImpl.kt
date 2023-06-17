package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.RMUDatabase
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel

class RMULocalDataSourceImpl(private val database: RMUDatabase): RMULocalDataSource {

    override suspend fun getCharacter(id: Long): CharacterEntity? {
        return database.characterDao().getById(id)
    }

    override suspend fun getCharacters(ids: List<Long>): List<CharacterEntity> {
        return database.characterDao().getByIds(ids)
    }

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        database.characterDao().insertAll(characters)
    }

    override suspend fun getEpisodes(ids: List<Long>): List<EpisodeEntity> {
        return database.episodeDao().getAllByIds(ids)
    }

    override suspend fun insertEpisodes(episodes: List<EpisodeEntity>) {
        database.episodeDao().insertAll(episodes)
    }
}