package com.gmail.uli153.rickmortyandulises.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSource
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSource
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity

class CharacterPagingData(
    private val localDataSource: RMULocalDataSource,
    private val remoteDataSource: RMURemoteDataSource,
    private val characterName: String,
    private val status: String?
): PagingSource<Int, CharacterEntity>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        val page = params.key ?: 1

        try {
            // Ask the remote server what characters match the search
            val searchIdResponse = remoteDataSource.getCharacterIds(page, characterName, status)
            val searchPageIds = searchIdResponse.results.mapNotNull { it.id.toLongOrNull() }
            val prevPage = searchIdResponse.info.prev
            val nextPage = searchIdResponse.info.next
            // Find those characters in cache
            val charactersInCache = localDataSource.getCharacters(searchPageIds)
            val allCharacters = ArrayList(charactersInCache)
            val charactersInCacheIds = charactersInCache.map { it.id }
            val charactersToRequestRemoteIds = searchPageIds.toMutableList().apply { removeAll(charactersInCacheIds) }
            if (charactersToRequestRemoteIds.size > 0) {
                // Fetch from remote server the characters not found in cache
                val remoteCharacters = remoteDataSource.getCharacters(charactersToRequestRemoteIds)
                localDataSource.insertCharacters(remoteCharacters)
                allCharacters.addAll(remoteCharacters)
            }

            if (searchPageIds.size != allCharacters.size) {
                return LoadResult.Error(Exception("Error fetching character ids"))
            }

            // This for loop ensures characters are sorted as the remote server sent
            val characters = mutableListOf<CharacterEntity>()
            for (id in searchPageIds) {
                characters.add(allCharacters.first { it.id == id })
            }

            return LoadResult.Page(data = characters, prevKey = prevPage, nextKey = nextPage)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}