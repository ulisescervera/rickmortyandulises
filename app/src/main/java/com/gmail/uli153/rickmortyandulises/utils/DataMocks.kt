package com.gmail.uli153.rickmortyandulises.utils

import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel

fun episodesMock(): List<EpisodeModel> {
    return mutableListOf<EpisodeModel>().apply {
        for (i in 0 until 20) {
            val episode = EpisodeModel(i.toLong(), "Episode mock $i", "", emptyList())
            add(episode)
        }
    }
}