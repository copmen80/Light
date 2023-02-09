package com.electro.light.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.electro.light.explore.model.local.LocationDAO
import com.electro.light.explore.model.local.Location

@Database(entities = [Location::class], version = 1)
abstract class LightDataBase : RoomDatabase() {
    abstract fun locationDao(): LocationDAO?
}