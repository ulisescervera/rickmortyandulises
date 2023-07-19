package com.gmail.uli153.rickmortyandulises.data.entities

data class CharacterIdsRemoteResponse(
    val data: CharacterIdsDataResponse
)

data class CharacterIdsDataResponse(
    val characters: ResourceIdsResponse
)

data class ResourceIdsResponse(
    val info: ResourceIdsInfoRemoteResponse,
    val results: List<ResourceIdResponse>
)

data class ResourceIdResponse(
    val id: String
)

data class ResourceIdsInfoRemoteResponse(
    val prev: Int?,
    val next: Int?
)