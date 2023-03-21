package com.electro.light.location.detailed.ui.model

data class ScheduleUiModel(
    val icon: Int?,
    val periodOfTime: String,
    val remainingTime: String,
    var isSelected: Boolean
)
