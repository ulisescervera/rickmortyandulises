package com.gmail.uli153.rickmortyandulises.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSource
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSource
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity

class CharacterPagingDataByIds(
    private val localDataSource: RMULocalDataSource,
    private val remoteDataSource: RMURemoteDataSource,
    private val ids: List<Long>,
    private val pageSize: Int = 20
): PagingSource<Int, CharacterEntity>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        val page = params.key ?: 0
        val prevPage = if (page > 0) page - 1 else null
        val nextPage = if (((page + 1) * pageSize) < ids.size) page + 1 else null

        val pageIds = if (ids.size > 0) {
            ids.subList(page * pageSize, Math.min(ids.size - 1, page.plus(1) * pageSize))
        } else ids

        try {
            // Find those characters in cache
            val charactersInCache = localDataSource.getCharacters(pageIds)
            val allCharacters = ArrayList(charactersInCache)
            val charactersInCacheIds = charactersInCache.map { it.id }
            val charactersToRequestRemoteIds = pageIds.toMutableList().apply { removeAll(charactersInCacheIds) }
            if (charactersToRequestRemoteIds.size > 0) {
                // Fetch from remote server the characters not found in cache
                val remoteCharacters = remoteDataSource.getCharacters(charactersToRequestRemoteIds)
                localDataSource.insertCharacters(remoteCharacters)
                allCharacters.addAll(remoteCharacters)
            }

            if (pageIds.size != allCharacters.size) {
                return LoadResult.Error(Exception("Error fetching character ids"))
            }

            // This for loop ensures characters are sorted as the remote server sent
            val characters = mutableListOf<CharacterEntity>()
            for (id in pageIds) {
                characters.add(allCharacters.first { it.id == id })
            }

            return LoadResult.Page(data = characters, prevKey = prevPage, nextKey = nextPage)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}