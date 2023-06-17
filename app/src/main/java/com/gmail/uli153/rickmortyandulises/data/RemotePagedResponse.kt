package com.gmail.uli153.rickmortyandulises.data

data class RemotePagedResponse<T>(
    val info: InfoResponse,
    val results: List<T>
)

data class InfoResponse(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
