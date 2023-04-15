package com.electro.light.location.detailed.ui

import com.electro.light.location.detailed.ui.model.DayUiModel

sealed class DetailedEvent {
    data class ScheduleWeek(val list: ArrayList<DayUiModel>) : DetailedEvent()
    data class ScheduleDay(val scheduleWeek: DayUiModel) : DetailedEvent()
}