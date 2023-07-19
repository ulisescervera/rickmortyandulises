package com.gmail.uli153.rickmortyandulises.domain.usecases.character

import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import kotlinx.coroutines.flow.Flow

class GetAllCharacters(private val repository: RMURepository) {

    operator fun invoke(name: String, status: CharacterStatus?): Flow<PagingData<CharacterModel>> {
        return repository.getAllCharacters(name, status)
    }
}