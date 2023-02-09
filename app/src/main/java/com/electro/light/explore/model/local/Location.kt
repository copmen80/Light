package com.electro.light.explore.model.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "locations")
data class Location(
    @PrimaryKey val name: String,
    val latitude: String,
    val longitude: String
) : Parcelable