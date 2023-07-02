package com.gmail.uli153.rickmortyandulises.domain

import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import kotlinx.coroutines.flow.Flow

interface RMURepository {

    fun getCharacters(name: String, status: CharacterStatus?): Flow<PagingData<CharacterModel>>
    suspend fun getCharacterById(id: Long): CharacterModel?

    fun getPagedCharactersById(ids: List<Long>): Flow<PagingData<CharacterModel>>

    fun getEpisodesByIds(ids: List<Long>): Flow<List<EpisodeModel?>>
}