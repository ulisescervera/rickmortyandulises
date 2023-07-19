/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.data.entities

data class EpisodeIdsRemoteResponse(
    val data: EpisodeIdsDataResponse
)

data class EpisodeIdsDataResponse(
    val episodes: ResourceIdsResponse
)