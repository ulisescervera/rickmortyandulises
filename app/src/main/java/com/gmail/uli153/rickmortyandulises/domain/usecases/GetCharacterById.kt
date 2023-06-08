package com.gmail.uli153.rickmortyandulises.domain.usecases

import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel

class GetCharacterById(private val repository: RMURepository) {

    suspend operator fun invoke(id: Long): CharacterModel? {
        return repository.getCharacterById(id)
    }
}