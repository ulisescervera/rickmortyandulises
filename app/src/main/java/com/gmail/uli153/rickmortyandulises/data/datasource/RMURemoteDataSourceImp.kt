package com.gmail.uli153.rickmortyandulises.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gmail.uli153.rickmortyandulises.data.ApiService
import com.gmail.uli153.rickmortyandulises.data.RMUDatabase
import com.gmail.uli153.rickmortyandulises.data.RMURemoteMediator
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.utils.Constants
import com.gmail.uli153.rickmortyandulises.utils.PreferenceUtils
import kotlinx.coroutines.flow.Flow

class RMURemoteDataSourceImp(
    private val database: RMUDatabase,
    private val apiService: ApiService,
    private val preferenceUtils: PreferenceUtils
): RMURemoteDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(name: String, status: String?): Flow<PagingData<CharacterEntity>> {
        val mediator = RMURemoteMediator(preferenceUtils, database, apiService, Constants.CACHE_VALID_TIME_MILLIS, name, status)
        return Pager(
            config= PagingConfig(pageSize = Constants.PAGE_SIZE),
            remoteMediator = mediator,
            pagingSourceFactory = { database.characterDao().getAllBy(name, status) }
        ).flow
    }

}