package com.electro.light.location.common.data

import com.electro.light.location.explore.data.remote.LightApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepository(
    private val lightApi: LightApi,
    private val locationDao: LocationDAO
) {

    //TODO реалізація для ligth Api
    //TODO rename db fun
    //TODO перевірка на помилку при запросі з APi
    suspend fun getAllLocations(): List<Location> {
        var listOfLocations: List<Location>
        withContext(Dispatchers.IO) {
            listOfLocations = locationDao.getAllLocations()
        }
        return listOfLocations
    }

    suspend fun addLocation(location: Location) {
        withContext(Dispatchers.IO) {
            locationDao.addLocation(location)
        }
    }

    suspend fun deleteLocation(locationName: String) {
        withContext(Dispatchers.IO) {
            locationDao.deleteLocation(locationName)
        }
    }
}
