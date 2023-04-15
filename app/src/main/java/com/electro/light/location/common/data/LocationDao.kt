package com.electro.light.location.common.data

import androidx.room.*


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocation(location: Location)

    @Query("DELETE FROM locations WHERE name = :locationName")
    fun deleteLocation(locationName: String)

    @Query("SELECT * FROM locations")
    fun getAllLocations(): List<Location>

}