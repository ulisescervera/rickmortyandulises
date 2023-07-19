package com.gmail.uli153.rickmortyandulises.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSource
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSource
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.ResourceIdsResponse

class CharacterPagingData(
    localDataSource: RMULocalDataSource,
    remoteDataSource: RMURemoteDataSource,
    private val characterName: String,
    private val status: String?
): ResourcePagingData<CharacterEntity>(localDataSource, remoteDataSource) {

    override suspend fun getRemoteIdsPageData(page: Int): ResourceIdsResponse {
        return remoteDataSource.getCharacterIds(page, characterName, status)
    }

    override suspend fun getLocalResource(ids: List<Long>): List<CharacterEntity> {
        return localDataSource.getCharacters(ids)
    }

    override suspend fun getRemoteResource(ids: List<Long>): List<CharacterEntity> {
        return remoteDataSource.getCharacters(ids)
    }

    override suspend fun saveRemoteResource(resources: List<CharacterEntity>) {
        localDataSource.insertCharacters(resources)
    }
}