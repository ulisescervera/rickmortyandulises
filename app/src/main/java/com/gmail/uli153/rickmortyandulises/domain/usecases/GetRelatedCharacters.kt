package com.gmail.uli153.rickmortyandulises.domain.usecases

import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.utils.UIState
import kotlinx.coroutines.flow.Flow

class GetRelatedCharacters(private val repository: RMURepository) {

    operator fun invoke(characterId: Long?, episodes: List<EpisodeModel?>): Flow<PagingData<CharacterModel>> {
        val characterOcurrences = mutableMapOf<Long, Int>()
        episodes.filterNotNull().flatMap { episode -> episode.characters }.forEach { id ->
            characterOcurrences[id] = (characterOcurrences[id] ?: 0) + 1
        }
        val relatedCharacterIds = characterOcurrences.entries
            .filter { it.key != characterId } // removed current selected character from related list
            .sortedByDescending { it.value } // sorted by ocurrences (more related character)
            .map { it.key }

        return repository.getPagedCharactersById(relatedCharacterIds)
    }
}