package com.gmail.uli153.rickmortyandulises.domain.models

data class EpisodeModel(
    val id: Long,
    val name: String,
    val date: String,
    val characters: List<Long>
)