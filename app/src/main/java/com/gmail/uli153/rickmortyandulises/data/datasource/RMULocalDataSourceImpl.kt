package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.RMUDatabase
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity

class RMULocalDataSourceImpl(private val database: RMUDatabase): RMULocalDataSource {

    override suspend fun getCharacterBy(id: Long): CharacterEntity? {
        return database.characterDao().getById(id)
    }
}