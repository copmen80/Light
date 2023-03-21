package com.electro.light.location.detailed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.electro.light.R
import com.electro.light.location.common.data.Location
import com.electro.light.location.common.data.LocationRepository
import com.electro.light.location.detailed.ui.model.DayUiModel
import com.electro.light.location.detailed.ui.model.ScheduleUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class DetailedViewModel(
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val eventChannel = Channel<DetailedEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val scheduleList = arrayListOf(
        ScheduleUiModel(R.drawable.ic_power, "00:00 - 04:00", "4г", false),
        ScheduleUiModel(R.drawable.ic_flash_off, "04:00 - 08:00", "4г", false),
        ScheduleUiModel(null, "08:00 - 12:00", "4г", true),
        ScheduleUiModel(R.drawable.ic_power, "12:00 - 16:00", "4г", false),
        ScheduleUiModel(null, "16:00 - 20:00", "4г", false),
        ScheduleUiModel(R.drawable.ic_flash_off, "20:00 - 00:00", "4г", false),
    )

    private val scheduleList2 = arrayListOf(
        ScheduleUiModel(R.drawable.ic_power, "00:00 - 02:00", "2г", false),
        ScheduleUiModel(R.drawable.ic_flash_off, "02:00 - 08:00", "6г", false),
        ScheduleUiModel(null, "08:00 - 10:00", "2г", true),
        ScheduleUiModel(R.drawable.ic_power, "10:00 - 16:00", "6г", false),
        ScheduleUiModel(null, "16:00 - 18:00", "2г", false),
        ScheduleUiModel(R.drawable.ic_flash_off, "18:00 - 00:00", "6г", false),
    )

    private var dayList = arrayListOf(
        DayUiModel("Пн", false, false, scheduleList),
        DayUiModel("Вт", false, false, scheduleList2),
        DayUiModel("Ср", false, false, scheduleList),
        DayUiModel("Чт", false, false, scheduleList2),
        DayUiModel("Пт", false, false, scheduleList),
        DayUiModel("Сб", false, false, scheduleList2),
        DayUiModel("Нд", false, false, scheduleList),
    )

    private fun setActiveDay(list: ArrayList<DayUiModel>) {

        val calendar: Calendar = Calendar.getInstance(Locale.getDefault())
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)

        for (i in list.indices) {
            if (i == day - 2) {
                list[i].isSelected = true
                list[i].isCurrentDay = true
            }
        }
        dayList = list
    }

    fun getScheduleForWeek() {
        setActiveDay(dayList)
        viewModelScope.launch {
            eventChannel.send(
                DetailedEvent.ScheduleWeekData(
                    dayList
                )
            )
            eventChannel.send(
                DetailedEvent.ScheduleWeek(
                    dayList.first { it.isSelected }
                )
            )
        }
    }

    fun deleteLocation(name: String) {
        viewModelScope.launch {
            locationRepository.deleteLocation(name)
        }
    }

    fun getScheduleForSelectedDay(selectedDay: DayUiModel) {
        viewModelScope.launch {
            eventChannel.send(DetailedEvent.ScheduleWeek(selectedDay))
        }
    }

    fun setDay(dayUiModel: DayUiModel) {
        dayList.forEach {
            it.isSelected = false
        }

        dayList.forEach {
            if (it.day == dayUiModel.day) {
                it.isSelected = true
            }
        }
        viewModelScope.launch {
            eventChannel.send(DetailedEvent.ScheduleWeekData(dayList))
        }
    }
}
