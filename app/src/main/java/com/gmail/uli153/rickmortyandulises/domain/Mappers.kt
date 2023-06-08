package com.gmail.uli153.rickmortyandulises.domain

import android.net.Uri
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterGender
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import java.util.Date

fun CharacterEntity.toModel(): CharacterModel {
    val created = Formatters().remoteDateFormatter.parse(this.created) ?: Date()
    val episodeIds = this.episodes.mapNotNull { Uri.parse(it).pathSegments.last()?.toLongOrNull() }
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
        episodes = episodeIds,
        url = this.url,
        created = created
    )
}