package com.gmail.uli153.rickmortyandulises.data.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<CharacterEntity>)

    @Query("SELECT * FROM characters WHERE " +
            "name LIKE '%' || :name  || '%' " +
            "AND (:status IS NULL OR status LIKE :status)")
    fun getAllBy(name: String, status: String?): PagingSource<Int, CharacterEntity>

//    @Query("SELECT * FROM characters")
//    fun getAllBy(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characters WHERE id LIKE :id LIMIT 1")
    fun getById(id: Long): CharacterEntity?

    @Query("DELETE FROM characters")
    suspend fun clearAll()

}