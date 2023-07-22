package com.gmail.uli153.rickmortyandulises.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("episodes")
data class EpisodeEntity(
    @PrimaryKey override val id: Long,
    val name: String,
    val date: String,
    val characters: List<Long>
): BaseEntity
