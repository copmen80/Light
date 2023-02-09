package com.electro.light.explore.model

import com.electro.light.explore.model.local.LocationDAO
import com.electro.light.explore.model.remote.LightService

class LightRepository(
    private val lightService: LightService,
    private val locationDao: LocationDAO
) {

    suspend fun addNewLocation() {
        TODO()
    }
}