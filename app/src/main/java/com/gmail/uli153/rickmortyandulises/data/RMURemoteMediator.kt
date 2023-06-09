package com.gmail.uli153.rickmortyandulises.data

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.utils.PreferenceUtils
import retrofit2.HttpException
import java.util.Date

typealias RMUCachePolicy = Long
@OptIn(ExperimentalPagingApi::class)
class RMURemoteMediator(
    private val preferenceUtils: PreferenceUtils,
    private val database: RMUDatabase,
    private val apiService: ApiService,
    private val cachePolicy: RMUCachePolicy,
    private val name: String,
    private val status: String?
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun initialize(): InitializeAction {
        val lastUpdate = preferenceUtils.getCharacterRefreshDate() ?: return InitializeAction.LAUNCH_INITIAL_REFRESH
        val timeSinceLastUpdate = lastUpdate.time - Date().time
        if (timeSinceLastUpdate > cachePolicy) {
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val page: Int? = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.pages.lastOrNull()?.data?.lastOrNull()?.nextPage
            }

            if (page == null) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            val remoteResponse = apiService.getAllCharacters(page, name, status)
            val responseBody = remoteResponse.body()
            if (responseBody != null) {
                val nextPage = responseBody.info.next?.let { Uri.parse(it).getQueryParameter("page")?.toIntOrNull() }
                val prevPage = responseBody.info.prev?.let { Uri.parse(it).getQueryParameter("page")?.toIntOrNull() }
                val characters = responseBody.results.map { it.copy(prevPage = prevPage, nextPage = nextPage) }

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.characterDao().clearAll()
                    }
                    database.characterDao().insertAll(characters)
                }

                if (loadType == LoadType.REFRESH) {
                    preferenceUtils.updateCharacterRefreshDate()
                }

                return MediatorResult.Success(endOfPaginationReached = nextPage == null)
            } else {
                return MediatorResult.Error(HttpException(remoteResponse))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}