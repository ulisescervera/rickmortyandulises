package com.gmail.uli153.rickmortyandulises.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterOrigin,
    val location: CharacterLocation,
    val image: String,
    @SerializedName("episode") val episodes: List<String>,
    val url: String,
    val created: String,
    val prevPage: Int? = null,
    val nextPage: Int? = null
)

data class CharacterOrigin(
    val name: String,
    val url: String
)

data class CharacterLocation(
    val name: String,
    val url: String
)
