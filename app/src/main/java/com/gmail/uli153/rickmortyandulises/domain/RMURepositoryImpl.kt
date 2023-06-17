package com.gmail.uli153.rickmortyandulises.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSource
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSource
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.domain.paging.CharacterPagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RMURepositoryImpl(
    private val localDataSource: RMULocalDataSource,
    private val remoteDataSource: RMURemoteDataSource
): RMURepository {

    override fun getCharacters(name: String, status: CharacterStatus?): Flow<PagingData<CharacterModel>> {
        val safeName = name.trim()
        val stat = status?.name?.lowercase()
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { CharacterPagingData(localDataSource, remoteDataSource, safeName, stat) }
        ).flow.map { pagingData -> pagingData.map { it.toModel() } }
    }

    override suspend fun getCharacterById(id: Long): CharacterModel? {
        return localDataSource.getCharacter(id)?.toModel()
    }

    override fun getEpisodesByIds(ids: List<Long>): Flow<List<EpisodeModel?>> {
        return flow {
            val localEpisodes = localDataSource.getEpisodes(ids).map { it.toModel() }
            val episodes = mutableListOf<EpisodeModel?>()
            for (id in ids) {
                episodes.add(localEpisodes.find { it.id == id })
            }
            emit(episodes)

            val foundEpisodesInCache = episodes.mapNotNull { it?.id }
            val episodesToRequestRemote = ids.toMutableList().apply { removeAll(foundEpisodesInCache) }
            if (episodesToRequestRemote.isNotEmpty()) {
                val remoteEpisodes = remoteDataSource.getEpisodesByIds(episodesToRequestRemote)
                localDataSource.insertEpisodes(remoteEpisodes)
                val allEpisodes = remoteEpisodes.map { it.toModel() }.toMutableList().apply { addAll(localEpisodes) }
                episodes.clear()
                for (id in ids) {
                    episodes.add(allEpisodes.find { it.id == id })
                }
                emit(allEpisodes)
            }
        }
    }
}