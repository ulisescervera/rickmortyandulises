/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.domain.usecases.episode

import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import kotlinx.coroutines.flow.Flow

class GetAllEpisodes(private val repository: RMURepository) {

    operator fun invoke(): Flow<PagingData<EpisodeModel>> {
        return repository.getAllEpisodes()
    }
}