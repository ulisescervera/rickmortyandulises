package com.gmail.uli153.rickmortyandulises.domain.models

/**
 * Created by Ulises on 20/7/23.
 */
data class LocationModel(
    val id: Long,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Long>
)
