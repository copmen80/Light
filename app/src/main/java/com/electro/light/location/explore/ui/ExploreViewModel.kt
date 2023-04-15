package com.electro.light.location.explore.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.electro.light.location.common.data.LocationRepository
import com.electro.light.location.explore.ui.mapper.LocationToLocationUiModelMapper
import com.electro.light.location.explore.ui.mapper.ScheduleToDayUiModelMapper
import com.electro.light.location.explore.ui.model.LocationUiModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import java.util.*

class ExploreViewModel(
    private val locationRepository: LocationRepository,
) : ViewModel() {

    //TODO pass mapper from constructor
    private val locationToLocationUiModelMapper: LocationToLocationUiModelMapper =
        LocationToLocationUiModelMapper()
    private val scheduleToDayUiModelMapper: ScheduleToDayUiModelMapper =
        ScheduleToDayUiModelMapper()

    //TODO add loading to content
    private val _content = MutableStateFlow<List<LocationUiModel>>(emptyList())
    val content = _content.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                // TODO asinc request for allLocation and schedules
                val allLocations = locationRepository.getAllLocationsFormDb()
                val result = locationRepository.getScheduleForWeek()
                val listOfLocationUiModel = allLocations.map {
                    locationToLocationUiModelMapper.map(it)
                }

                //TODO it should be moved to mapper
                result.body()?.let { scheduleResponse ->
                    listOfLocationUiModel.forEach { locationUiModel ->
                        for (i in scheduleResponse.schedule.indices) {
                            if (locationUiModel.group - 1 == i) {
                                locationUiModel.schedule = scheduleResponse.schedule[i].map {
                                    scheduleToDayUiModelMapper.map(it)
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    locationUiModel.remainingTime = parseTime(locationUiModel)
                                }
                            }
                        }
                    }
                }

                _content.emit(listOfLocationUiModel)
            } catch (e: Exception) {
                e.printStackTrace()
                //TODO emit Error
            }
        }
    }

    fun deleteLocation(locationName: String) {
        //TODO change getAllLocationsFormDb to Flow and use Flow.combine with schedule request
        viewModelScope.launch {
            locationRepository.deleteLocationFromDb(locationName)

            val updatedList = locationRepository.getAllLocationsFormDb()
                .map { locationToLocationUiModelMapper.map(it) }

            updatedList.forEach {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    it.remainingTime = parseTime(it)
                }
            }
            _content.emit(updatedList)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun parseTime(location: LocationUiModel): String {
        //TODO from LocalTime to JodaTime
        //TODO put parseTime to mapper
        val calendar = Calendar.getInstance(Locale.getDefault())
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK) - 2

        location.schedule?.get(day)?.daySchedule?.forEach {
            val periodsOfTime = it.periodOfTime.split("-")

            val start = LocalTime.parse(periodsOfTime[0])
            val end = if (periodsOfTime[1] != "00:00") {
                LocalTime.parse(periodsOfTime[1])
            } else {
                LocalTime.parse("23:59:59")
            }

            val currentTime = LocalTime.now()
            if (currentTime.isBefore(end) && currentTime.isAfter(start)) {
                val duration = Duration.between(currentTime, end)
                return "${duration?.toHours()}г ${duration?.toMinutesPart()}хв"
            }
        }
        return "Error"
    }
}

// fix delete fun
// change fun parseTIME return
// add push notification with WorkManager