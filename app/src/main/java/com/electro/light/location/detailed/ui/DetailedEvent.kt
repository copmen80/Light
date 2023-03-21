package com.electro.light.location.detailed.ui

import com.electro.light.location.detailed.ui.model.DayUiModel

sealed class DetailedEvent {
    data class ScheduleWeekData(val list: ArrayList<DayUiModel>) : DetailedEvent()
    data class ScheduleWeek(val scheduleWeek: DayUiModel) : DetailedEvent()
}