package com.gmail.uli153.rickmortyandulises.data

import android.net.Uri
import com.gmail.uli153.rickmortyandulises.data.dto.CharacterDTO
import com.gmail.uli153.rickmortyandulises.data.dto.EpisodeDTO
import com.gmail.uli153.rickmortyandulises.data.dto.LocationDTO
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterEntity
import com.gmail.uli153.rickmortyandulises.data.entities.EpisodeEntity
import com.gmail.uli153.rickmortyandulises.data.entities.LocationEntity
import com.gmail.uli153.rickmortyandulises.domain.Formatters
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterGender
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.domain.models.LocationModel
import com.gmail.uli153.rickmortyandulises.domain.toModel
import java.util.Date

fun CharacterDTO.toEntity(): CharacterEntity {
    val episodeIds = this.episodes.mapNotNull { Uri.parse(it).pathSegments.last()?.toLongOrNull() }
    return CharacterEntity(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = this.origin,
        location = this.location,
        image = this.image,
        episodes = episodeIds,
        url = this.url,
        created = this.created
    )
}

fun EpisodeDTO.toEntity(): EpisodeEntity {
    val characterIds = this.characters.mapNotNull { Uri.parse(it).pathSegments.lastOrNull()?.toLongOrNull() }
    return EpisodeEntity(this.id, this.name, this.date, characterIds)
}

fun LocationDTO.toEntity(): LocationEntity {
    val residentIds = this.residents.mapNotNull { it.split("/").lastOrNull()?.toLongOrNull() }
    return LocationEntity(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        residents = residentIds
    )
}