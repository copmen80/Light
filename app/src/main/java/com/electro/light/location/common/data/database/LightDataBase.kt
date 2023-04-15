package com.electro.light.location.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.electro.light.location.common.data.LocationDao
import com.electro.light.location.common.data.Location

@Database(entities = [Location::class], version = 1)
abstract class LightDataBase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao?
}