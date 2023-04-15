package com.electro.light.location.explore.ui.mapper

import com.electro.light.location.common.data.response.Schedule
import com.electro.light.location.detailed.ui.model.DayUiModel

class ScheduleToDayUiModelMapper {
    fun map(schedule: Schedule): DayUiModel {
        val shutdownToScheduleUiModelMapper = ShutdownToScheduleUiModelMapper()
        return DayUiModel(
            day = schedule.dayOfWeek,
            isCurrentDay = false,
            isSelected = false,
            daySchedule = schedule.shutdown.map {
                shutdownToScheduleUiModelMapper.map(it)
            }
        )
    }
}
