package com.gmail.uli153.rickmortyandulises.data.dto

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class EpisodeDTO(
    val id: Long,
    val name: String,
    @SerializedName("air_date") val date: String,
    val characters: List<String>
)