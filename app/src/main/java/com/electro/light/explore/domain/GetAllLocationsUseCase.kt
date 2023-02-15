package com.electro.light.explore.domain

import com.electro.light.explore.data.LightRepository
import com.electro.light.explore.data.local.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllLocationsUseCase(
    private val lightRepository: LightRepository
) {
    /*suspend operator fun invoke(): List<Location> {
        return withContext(Dispatchers.IO) {
            lightRepository.getAllLocations()
        }
    }*/
}
