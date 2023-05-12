package com.electro.light.location.explore.domain

import com.electro.light.location.common.data.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetScheduleForWeekUseCase(private val locationRepository: LocationRepository) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        locationRepository.getScheduleForWeek()
    }
}