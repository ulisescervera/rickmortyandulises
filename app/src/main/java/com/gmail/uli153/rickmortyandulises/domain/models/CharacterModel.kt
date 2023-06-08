package com.gmail.uli153.rickmortyandulises.domain.models

import java.util.Date

data class CharacterModel(
    val id: Long,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender,
    val origin: String,
    val location: String,
    val image: String,
    val episodes: List<Long>,
    val url: String,
    val created: Date
)

enum class CharacterStatus {
    Alive, Dead, Unknown
}

enum class CharacterGender {
    Female, Male, Genderless, Unknown
}