package com.electro.light.location.explore.ui.mapper

import com.electro.light.location.common.data.response.Shutdown
import com.electro.light.location.detailed.ui.model.ScheduleUiModel

class ShutdownToScheduleUiModelMapper {
    fun map(shutdown: Shutdown): ScheduleUiModel {
        return ScheduleUiModel(
            icon = null,
            periodOfTime = shutdown.range,
            remainingTime = shutdown.quantity,
            typeShutdown = shutdown.shutdownType
        )
    }
}
