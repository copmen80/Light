package com.electro.light.location.detailed.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayUiModel(
    val day: String,
    var isCurrentDay: Boolean,
    var isSelected: Boolean,
    val daySchedule: List<ScheduleUiModel>
) : Parcelable
