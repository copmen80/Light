package com.electro.light.location.explore.ui.mapper

import com.electro.light.location.common.data.Location
import com.electro.light.location.explore.ui.model.LocationUiModel

class LocationToLocationUiModelMapper {
    fun map(location: Location): LocationUiModel {
        return LocationUiModel(
            name = location.name,
            icon = location.icon,
            group = location.group,
            schedule = null,
            remainingTime = null,
            typeShutdown = null
        )
    }
}
