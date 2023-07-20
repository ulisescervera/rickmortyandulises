/**
 * Created by Ulises on 20/7/23.
 */
package com.gmail.uli153.rickmortyandulises.data.entities

data class LocationIdsRemoteResponse(
    val data: LocationIdsDataResponse
)

data class LocationIdsDataResponse(
    val locations: ResourceIdsResponse
)