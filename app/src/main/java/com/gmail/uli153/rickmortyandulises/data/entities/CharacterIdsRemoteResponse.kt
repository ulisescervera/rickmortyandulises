package com.gmail.uli153.rickmortyandulises.data.entities

data class CharacterIdsRemoteResponse(
    val data: CharacterIdsDataResponse
)

data class CharacterIdsDataResponse(
    val characters: CharacterIdsResponse
)

data class CharacterIdsResponse(
    val info: CharacterIdsInfoRemoteResponse,
    val results: List<CharacterIdResponse>
)

data class CharacterIdResponse(
    val id: String
)

data class CharacterIdsInfoRemoteResponse(
    val prev: Int?,
    val next: Int?
)