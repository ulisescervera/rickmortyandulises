package com.gmail.uli153.rickmortyandulises.domain.usecases.character

import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.LocationModel
import kotlinx.coroutines.flow.Flow

class GetCharactersInLocation(private val repository: RMURepository) {

    operator fun invoke(location: LocationModel): Flow<PagingData<CharacterModel>> {
        return repository.getPagedCharactersById(location.residents)
    }
}