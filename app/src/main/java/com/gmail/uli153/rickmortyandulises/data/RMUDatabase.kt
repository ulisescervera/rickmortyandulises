package com.gmail.uli153.rickmortyandulises.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gmail.uli153.rickmortyandulises.data.RMUDatabase.Companion.DATABASE_VERSION
import com.gmail.uli153.rickmortyandulises.data.daos.CharacterDao
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Database(entities = [CharacterEntity::class], version = DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class RMUDatabase: RoomDatabase() {

    companion object {
        const val DATABASE_VERSION: Int = 1
        const val DATABASE_NAME: String = "rick_morty_ulises_database"

        fun buildDatabase(context: Context): RMUDatabase {
            return Room.databaseBuilder(context, RMUDatabase::class.java, DATABASE_NAME)
                .addCallback(AkihabaraDatabaseCallack())
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun characterDao(): CharacterDao

}

class AkihabaraDatabaseCallack: RoomDatabase.Callback() {

    private val scope = CoroutineScope(SupervisorJob())

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

    }


}