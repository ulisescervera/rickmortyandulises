package com.gmail.uli153.rickmortyandulises.data.datasource

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity

interface RMULocalDataSource {

    suspend fun getCharacterBy(id: Long): CharacterEntity?
}