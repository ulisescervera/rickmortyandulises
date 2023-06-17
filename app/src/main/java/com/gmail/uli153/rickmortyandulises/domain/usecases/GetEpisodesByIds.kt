package com.gmail.uli153.rickmortyandulises.domain.usecases

import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import kotlinx.coroutines.flow.Flow

class GetEpisodesByIds(private val repository: RMURepository) {

    operator fun invoke(ids: List<Long>): Flow<List<EpisodeModel?>> {
        return repository.getEpisodesByIds(ids.sorted())
    }
}