package com.gmail.uli153.rickmortyandulises.domain.usecases

import com.gmail.uli153.rickmortyandulises.domain.usecases.character.GetAllCharacters
import com.gmail.uli153.rickmortyandulises.domain.usecases.character.GetCharacterById
import com.gmail.uli153.rickmortyandulises.domain.usecases.character.GetRelatedCharacters

data class CharacterUseCases(
    val getAllCharacters: GetAllCharacters,
    val getCharacterById: GetCharacterById,
    val getRelatedCharacters: GetRelatedCharacters
)