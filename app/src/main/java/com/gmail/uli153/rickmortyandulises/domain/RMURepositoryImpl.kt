package com.gmail.uli153.rickmortyandulises.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSource
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSource
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RMURepositoryImpl(
    private val localDataSource: RMULocalDataSource,
    private val remoteDataSource: RMURemoteDataSource
): RMURepository {

    override fun getCharacters(name: String, status: CharacterStatus?): Flow<PagingData<CharacterModel>> {
        val safeName = name.trim()
        val stat = status?.name?.lowercase()
        return remoteDataSource.getCharacters(safeName, stat).map { pagingData -> pagingData.map { it.toModel() } }
    }

    override suspend fun getCharacterById(id: Long): CharacterModel? {
        return localDataSource.getCharacterBy(id)?.toModel()
    }
}