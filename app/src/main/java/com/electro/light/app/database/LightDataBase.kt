package com.electro.light.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.electro.light.location.common.data.LocationDAO
import com.electro.light.location.common.data.Location

@Database(entities = [Location::class], version = 1)
abstract class LightDataBase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDAO?
}