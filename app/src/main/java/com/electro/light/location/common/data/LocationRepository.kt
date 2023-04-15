package com.electro.light.location.common.data

import com.electro.light.location.common.data.remote.LightApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepository(
    private val lightApi: LightApi,
    private val locationDao: LocationDao
) {
    // TODO pass dispatcher.IO from constructor

    //TODO create new Schedule repository

    //TODO add domain model

    //TODO add interface for repository

    suspend fun getAllLocationsFormDb(): List<Location> =
        withContext(Dispatchers.IO) {
            locationDao.getAllLocations()
        }

    suspend fun addLocationToDb(location: Location) {
        withContext(Dispatchers.IO) {
            locationDao.addLocation(location)
        }
    }

    suspend fun deleteLocationFromDb(locationName: String) {
        withContext(Dispatchers.IO) {
            locationDao.deleteLocation(locationName)
        }
    }

    suspend fun getScheduleForWeek() = lightApi.getScheduleForWeek()

    suspend fun getScheduleForWeekByGroupId(group: Int) =
        lightApi.getScheduleForWeekByGroupId(group)
}
