/**
 * Created by Ulises on 20/7/23.
 */
package com.gmail.uli153.rickmortyandulises.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.uli153.rickmortyandulises.data.entities.LocationEntity

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)

    @Query("SELECT * FROM locations WHERE id IN (:ids) ORDER BY id")
    fun getAllByIds(ids: List<Long>): List<LocationEntity>
}