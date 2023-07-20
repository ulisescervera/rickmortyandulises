/**
 * Created by Ulises on 20/7/23.
 */
package com.gmail.uli153.rickmortyandulises.domain.paging

import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSource
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSource
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity
import com.gmail.uli153.rickmortyandulises.data.entities.LocationEntity
import com.gmail.uli153.rickmortyandulises.data.entities.ResourceIdsResponse

class LocationPagingData(
    localDataSource: RMULocalDataSource,
    remoteDataSource: RMURemoteDataSource,
): ResourcePagingData<LocationEntity>(localDataSource, remoteDataSource) {

    override suspend fun getRemoteIdsPageData(page: Int): ResourceIdsResponse {
        return remoteDataSource.getLocationIds(page)
    }

    override suspend fun getLocalResource(ids: List<Long>): List<LocationEntity> {
        return localDataSource.getLocations(ids)
    }

    override suspend fun getRemoteResource(ids: List<Long>): List<LocationEntity> {
        return remoteDataSource.getLocations(ids)
    }

    override suspend fun saveRemoteResource(resources: List<LocationEntity>) {
        localDataSource.insertLocations(resources)
    }

}