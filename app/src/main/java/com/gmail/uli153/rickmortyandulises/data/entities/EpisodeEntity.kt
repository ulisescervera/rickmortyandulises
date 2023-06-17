package com.gmail.uli153.rickmortyandulises.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("episodes")
data class EpisodeEntity(
    @PrimaryKey val id: Long,
    val name: String,
    @SerializedName("air_date") val date: String,
    val characters: List<String>
)
