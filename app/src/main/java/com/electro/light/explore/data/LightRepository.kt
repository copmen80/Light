package com.electro.light.explore.data

import com.electro.light.explore.data.local.Location
import com.electro.light.explore.data.local.LocationDAO
import com.electro.light.explore.data.remote.LightService

class LightRepository(
    private val lightService: LightService,
    private val locationDao: LocationDAO
) {

//    suspend fun getAllLocations(): List<Location> {
//        return locationDao.getAllLocations()
//    }
}