/**
 * Created by Ulises on 20/7/23.
 */
package com.gmail.uli153.rickmortyandulises.domain.usecases

import com.gmail.uli153.rickmortyandulises.domain.usecases.location.GetAllLocations

class LocationUseCases(
    val getAllLocations: GetAllLocations
)