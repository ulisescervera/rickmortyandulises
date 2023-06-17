package com.gmail.uli153.rickmortyandulises.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodes WHERE id IN (:ids) ORDER BY id")
    fun getAllByIds(ids: List<Long>): List<EpisodeEntity>
}