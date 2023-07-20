package com.gmail.uli153.rickmortyandulises.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ulises on 20/7/23.
 */
@Entity("locations")
data class LocationEntity(
    @PrimaryKey override val id: Long,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>
): BaseEntity
