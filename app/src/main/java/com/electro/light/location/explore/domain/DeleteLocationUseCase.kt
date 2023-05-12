package com.electro.light.location.explore.domain

import com.electro.light.location.common.data.LocationRepository

class DeleteLocationUseCase(private val locationRepository: LocationRepository) {
    suspend operator fun invoke(locationName: String) =
        locationRepository.deleteLocationFromDb(locationName)
}