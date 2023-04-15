package com.electro.light.location.detailed.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.electro.light.location.common.data.LocationRepository
import com.electro.light.location.detailed.ui.model.DayUiModel
import com.electro.light.location.explore.ui.mapper.ScheduleToDayUiModelMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.collections.ArrayList

class DetailedViewModel(
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val scheduleToDayUiModelMapper = ScheduleToDayUiModelMapper()

    private val eventChannel = Channel<DetailedEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val _content = MutableStateFlow<List<DayUiModel>>(emptyList())
    val content = _content.asStateFlow()

    private val errorMessage = MutableLiveData<String>()

    private suspend fun getDaysList(groupId: Int): List<DayUiModel> {
        val response = locationRepository.getScheduleForWeekByGroupId(groupId)
        val daysList = ArrayList<DayUiModel>()
        if (response.isSuccessful) {
            response.body()?.let { scheduleResponse ->
                _content.emit(
                    scheduleResponse.schedule.map {
                        scheduleToDayUiModelMapper.map(
                            it
                        )
                    })
            }
        } else {
            onError("Error : ${response.message()} ")
        }
        return daysList
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

/*
    private val scheduleList = arrayListOf(
        ScheduleUiModel(R.drawable.ic_power, "00:00 - 04:00", "4г"),
        ScheduleUiModel(R.drawable.ic_flash_off, "04:00 - 08:00", "4г"),
        ScheduleUiModel(null, "08:00 - 12:00", "4г"),
        ScheduleUiModel(R.drawable.ic_power, "12:00 - 16:00", "4г"),
        ScheduleUiModel(null, "16:00 - 20:00", "4г"),
        ScheduleUiModel(R.drawable.ic_flash_off, "20:00 - 00:00", "4г"),
    )

    private val scheduleList2 = arrayListOf(
        ScheduleUiModel(R.drawable.ic_power, "00:00 - 02:00", "2г"),
        ScheduleUiModel(R.drawable.ic_flash_off, "02:00 - 08:00", "6г"),
        ScheduleUiModel(null, "08:00 - 10:00", "2г"),
        ScheduleUiModel(R.drawable.ic_power, "10:00 - 16:00", "6г"),
        ScheduleUiModel(null, "16:00 - 18:00", "2г"),
        ScheduleUiModel(R.drawable.ic_flash_off, "18:00 - 00:00", "6г"),
    )

    private var daysList = arrayListOf(
        DayUiModel("Пн", isCurrentDay = false, isSelected = false, daySchedule = scheduleList),
        DayUiModel("Вт", isCurrentDay = false, isSelected = false, daySchedule = scheduleList2),
        DayUiModel("Ср", isCurrentDay = false, isSelected = false, daySchedule = scheduleList),
        DayUiModel("Чт", isCurrentDay = false, isSelected = false, daySchedule = scheduleList2),
        DayUiModel("Пт", isCurrentDay = false, isSelected = false, daySchedule = scheduleList),
        DayUiModel("Сб", isCurrentDay = false, isSelected = false, daySchedule = scheduleList2),
        DayUiModel("Нд", isCurrentDay = false, isSelected = false, daySchedule = scheduleList),
    )*/

    private suspend fun setCurrentDay(list: List<DayUiModel>) {
        val calendar: Calendar = Calendar.getInstance(Locale.getDefault())
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)

        for (i in list.indices) {
            if (i == day - 2) {
                list[i].isSelected = true
                list[i].isCurrentDay = true
            }
        }
        _content.emit(list)
    }

    fun getScheduleForWeek(groupId: Int) {
        viewModelScope.launch {
            getDaysList(groupId)
            setCurrentDay(_content.value)
            val schedule = _content.value
            if (schedule.isNotEmpty()) {
                eventChannel.send(
                    DetailedEvent.ScheduleWeek(
                        schedule as ArrayList<DayUiModel>
                    )
                )
                eventChannel.send(
                    DetailedEvent.ScheduleDay(
                        schedule.first { it.isSelected }
                    )
                )
            }
        }


    }

    fun deleteLocation(name: String) {
        viewModelScope.launch {
            locationRepository.deleteLocationFromDb(name)
        }
    }

    fun getScheduleForSelectedDay(selectedDay: DayUiModel) {
        viewModelScope.launch {
            eventChannel.send(DetailedEvent.ScheduleDay(selectedDay))
        }
    }

    fun setActiveDay(dayUiModel: DayUiModel) {
        content.value.forEach {
            it.isSelected = false
        }

        content.value.forEach {
            if (it.day == dayUiModel.day) {
                it.isSelected = true
            }
        }
        viewModelScope.launch {
            eventChannel.send(DetailedEvent.ScheduleWeek(content.value as ArrayList<DayUiModel>))
        }
    }
}
