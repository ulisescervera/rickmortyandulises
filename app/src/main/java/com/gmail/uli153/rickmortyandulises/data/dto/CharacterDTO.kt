package com.gmail.uli153.rickmortyandulises.data.dto

import androidx.room.PrimaryKey
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterLocation
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterOrigin
import com.google.gson.annotations.SerializedName

data class CharacterDTO(
    val id: Long,
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
