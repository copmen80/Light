package com.electro.light.location.explore.ui.model

import android.os.Parcelable
import com.electro.light.location.detailed.ui.model.DayUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationUiModel(
    val name: String,
    val icon: Int,
    val group: Int,
    var remainingTime: String?,
    var schedule: List<DayUiModel>?,
) : Parcelable