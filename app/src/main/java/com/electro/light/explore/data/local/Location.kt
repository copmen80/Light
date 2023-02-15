package com.electro.light.explore.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    @PrimaryKey val name: String,
    val latitude: String,
    val longitude: String
)