package com.electro.light.location.common.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey val name: String,
    val icon: Int,
    val group: String
)