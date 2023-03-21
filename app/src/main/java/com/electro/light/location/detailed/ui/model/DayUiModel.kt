package com.electro.light.location.detailed.ui.model

data class DayUiModel(
    val day: String,
    var isCurrentDay: Boolean,
    var isSelected: Boolean,
    val daySchedule: ArrayList<ScheduleUiModel>
)
