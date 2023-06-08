package com.gmail.uli153.rickmortyandulises.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.data.ApiService
import com.gmail.uli153.rickmortyandulises.data.RMUDatabase
import com.gmail.uli153.rickmortyandulises.data.RMURemoteMediator
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

class RMURemoteDataSourceImp(
    private val database: RMUDatabase,
    private val apiService: ApiService
): RMURemoteDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(name: String, status: String?): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config= PagingConfig(pageSize = 20),
            remoteMediator = RMURemoteMediator(database, apiService, name, status),
            pagingSourceFactory = { database.characterDao().getAllBy(name, status) }
        ).flow
    }

}