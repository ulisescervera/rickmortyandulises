package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface RMULocalDataSource {

    suspend fun getCharacterBy(id: Long): CharacterEntity?
}