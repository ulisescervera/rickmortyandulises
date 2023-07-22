package com.gmail.uli153.rickmortyandulises.domain

import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity
import com.gmail.uli153.rickmortyandulises.data.entities.LocationEntity
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterGender
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.domain.models.LocationModel
import java.util.Date

fun CharacterEntity.toModel(): CharacterModel {
    val created = Formatters.remoteDateFormatter.parse(this.created) ?: Date()
    return CharacterModel(
        id = this.id,
        name = this.name,
        status = CharacterStatus.values().first { it.name.lowercase() == this.status.lowercase() },
        species = this.species,
        type = this.type,
        gender = CharacterGender.values().first { it.name.lowercase() == this.gender.lowercase() },
        origin = this.origin.name,
        location = this.location.name,
        image = this.image,
        episodes = this.episodes,
        url = this.url,
        created = created
    )
}

fun EpisodeEntity.toModel(): EpisodeModel {
    return EpisodeModel(this.id, this.name, this.date, this.characters)
}

fun LocationEntity.toModel(): LocationModel {
    return LocationModel(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        residents = this.residents
    )
}