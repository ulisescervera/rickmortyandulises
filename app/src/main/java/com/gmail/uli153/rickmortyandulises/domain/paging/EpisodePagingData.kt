/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSource
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSource
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity
import com.gmail.uli153.rickmortyandulises.data.entities.ResourceIdsResponse
import com.gmail.uli153.rickmortyandulises.data.toEntity

class EpisodePagingData(
    localDataSource: RMULocalDataSource,
    remoteDataSource: RMURemoteDataSource,
): ResourcePagingData<EpisodeEntity>(localDataSource, remoteDataSource) {

    override suspend fun getRemoteIdsPageData(page: Int): ResourceIdsResponse {
        return remoteDataSource.getEpisodeIds(page)
    }

    override suspend fun getLocalResource(ids: List<Long>): List<EpisodeEntity> {
        return localDataSource.getEpisodes(ids)
    }

    override suspend fun getRemoteResource(ids: List<Long>): List<EpisodeEntity> {
        return remoteDataSource.getEpisodes(ids).map { it.toEntity() }
    }

    override suspend fun saveRemoteResource(resources: List<EpisodeEntity>) {
        localDataSource.insertEpisodes(resources)
    }

}