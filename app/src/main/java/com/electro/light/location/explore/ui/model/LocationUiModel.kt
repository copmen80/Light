package com.electro.light.location.explore.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationUiModel(
    val name: String,
    val icon: Int,
    val group: String
) : Parcelable