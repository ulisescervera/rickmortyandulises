/**
 * Created by Ulises on 20/7/23.
 */
package com.gmail.uli153.rickmortyandulises.domain.usecases.location

import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.models.LocationModel
import kotlinx.coroutines.flow.Flow

class GetAllLocations(private val repository: RMURepository) {

    operator fun invoke(): Flow<PagingData<LocationModel>> {
        return repository.getAllLocations()
    }
}