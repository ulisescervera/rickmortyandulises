/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSource
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSource
import com.gmail.uli153.rickmortyandulises.data.entities.BaseEntity
import com.gmail.uli153.rickmortyandulises.data.entities.ResourceIdsResponse

abstract class ResourcePagingData<T: BaseEntity>(
    protected val localDataSource: RMULocalDataSource,
    protected val remoteDataSource: RMURemoteDataSource,
): PagingSource<Int, T>() {

    abstract suspend fun getRemoteIdsPageData(page: Int): ResourceIdsResponse
    abstract suspend fun getLocalResource(ids: List<Long>): List<T>
    abstract suspend fun getRemoteResource(ids: List<Long>): List<T>
    abstract suspend fun saveRemoteResource(resources: List<T>)

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1

        try {
            // Ask the remote server what characters match the search
            val searchIdResponse = getRemoteIdsPageData(page)
            val searchPageIds = searchIdResponse.results.mapNotNull { it.id.toLongOrNull() }
            val prevPage = searchIdResponse.info.prev
            val nextPage = searchIdResponse.info.next
            // Find those characters in cache
            val resourcesInCache = getLocalResource(searchPageIds)
            val allResources = ArrayList(resourcesInCache)
            val resourcesInCacheIds = resourcesInCache.map { it.id }
            val resourcesToRequestRemoteIds = searchPageIds.toMutableList().apply { removeAll(resourcesInCacheIds) }
            if (resourcesToRequestRemoteIds.size > 0) {
                // Fetch from remote server the characters not found in cache
                val remoteResources = getRemoteResource(resourcesToRequestRemoteIds)
                saveRemoteResource(remoteResources)
                allResources.addAll(remoteResources)
            }

            if (searchPageIds.size != allResources.size) {
                return LoadResult.Error(Exception("Error fetching character ids"))
            }

            // This for loop ensures characters are sorted as the remote server sent
            val resources = mutableListOf<T>()
            for (id in searchPageIds) {
                resources.add(allResources.first { it.id == id })
            }

            return LoadResult.Page(data = resources, prevKey = prevPage, nextKey = nextPage)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}