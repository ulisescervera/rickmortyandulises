package com.gmail.uli153.rickmortyandulises.data.datasource

import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface RMURemoteDataSource {

    fun getCharacters(name: String, status: String?): Flow<PagingData<CharacterEntity>>
}