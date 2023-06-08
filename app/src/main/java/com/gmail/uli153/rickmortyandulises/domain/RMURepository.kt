package com.gmail.uli153.rickmortyandulises.domain

import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import kotlinx.coroutines.flow.Flow

interface RMURepository {

    fun getCharacters(name: String, status: CharacterStatus?): Flow<PagingData<CharacterModel>>
    suspend fun getCharacterById(id: Long): CharacterModel?
}