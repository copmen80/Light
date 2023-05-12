package com.electro.light.location.explore.domain

import com.electro.light.location.common.data.Location
import com.electro.light.location.common.data.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllLocationUseCase(private val locationRepository: LocationRepository) {
    suspend operator fun invoke(): List<Location> = withContext(Dispatchers.IO) {
        locationRepository.getAllLocationsFormDb()
    }
}