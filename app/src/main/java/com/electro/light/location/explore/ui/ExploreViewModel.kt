package com.electro.light.location.explore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.electro.light.location.common.data.Location
import com.electro.light.location.common.data.LocationRepository
import com.electro.light.location.explore.ui.mapper.LocationToLocationUiModelMapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val locationToLocationUiModelMapper: LocationToLocationUiModelMapper =
        LocationToLocationUiModelMapper()

    private val _content = MutableStateFlow<List<Location>>(emptyList())
    val content = _content.asStateFlow().map { list ->
        list.map { locationToLocationUiModelMapper.map(it) }
    }

    private val eventChannel = Channel<ExploreEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            try {
                _content.emit(locationRepository.getAllLocations())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteLocation(locationName: String) {
        viewModelScope.launch {
                locationRepository.deleteLocation(locationName)
            _content.emit(locationRepository.getAllLocations())
        }
    }
}
