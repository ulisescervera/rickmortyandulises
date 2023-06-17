package com.gmail.uli153.rickmortyandulises.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.uli153.rickmortyandulises.data.RMUDatabase.Companion.DATABASE_VERSION
import com.gmail.uli153.rickmortyandulises.data.daos.CharacterDao
import com.gmail.uli153.rickmortyandulises.data.daos.EpisodeDao
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity

@Database(entities = [CharacterEntity::class, EpisodeEntity::class], version = DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class RMUDatabase: RoomDatabase() {

    companion object {
        const val DATABASE_VERSION: Int = 1
        const val DATABASE_NAME: String = "rick_morty_ulises_database"

        fun buildDatabase(context: Context): RMUDatabase {
            return Room.databaseBuilder(context, RMUDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao

}