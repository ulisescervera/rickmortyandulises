package com.gmail.uli153.rickmortyandulises.domain.usecases

import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow

class GetPagedCharactersById(private val repository: RMURepository) {

    operator fun invoke(ids: List<Long>): Flow<PagingData<CharacterModel>> {
        return repository.getPagedCharactersById(ids)
    }
}