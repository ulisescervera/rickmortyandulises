package com.gmail.uli153.rickmortyandulises.domain.usecases

import com.gmail.uli153.rickmortyandulises.domain.usecases.episode.GetAllEpisodes
import com.gmail.uli153.rickmortyandulises.domain.usecases.episode.GetEpisodesByIds

data class EpisodeUseCases(
    val getAllEpisodes: GetAllEpisodes,
    val getEpisodesByIds: GetEpisodesByIds
)
