package com.gmail.uli153.rickmortyandulises.data.dto

import androidx.room.PrimaryKey

data class LocationDTO(
    val id: Long,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>
)