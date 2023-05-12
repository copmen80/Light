package com.electro.light.location.explore.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.electro.light.app.notification.NotifyWork
import com.electro.light.location.explore.domain.DeleteLocationUseCase
import com.electro.light.location.explore.domain.GetAllLocationUseCase
import com.electro.light.location.explore.domain.GetScheduleForWeekUseCase
import com.electro.light.location.explore.ui.mapper.LocationToLocationUiModelMapper
import com.electro.light.location.explore.ui.mapper.ScheduleToDayUiModelMapper
import com.electro.light.location.explore.ui.model.LocationUiModel
import com.electro.light.location.explore.ui.model.TimeModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ExploreViewModel(
    application: Application,
    private val getAllLocationUseCase: GetAllLocationUseCase,
    private val getScheduleForWeekUseCase: GetScheduleForWeekUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase,
) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application.applicationContext)

    val list = mutableListOf<WorkRequest>()

    //TODO pass mapper from constructor
    private val locationToLocationUiModelMapper: LocationToLocationUiModelMapper =
        LocationToLocationUiModelMapper()
    private val scheduleToDayUiModelMapper: ScheduleToDayUiModelMapper =
        ScheduleToDayUiModelMapper()

    //TODO add loading to content
    private val _content = MutableStateFlow<List<LocationUiModel>>(emptyList())
    val content = _content.asStateFlow()

    init {
        getLocations()
    }

    private fun getLocations() {
        clearWorkManager()
        viewModelScope.launch {
            try {
                // TODO async request for allLocation and schedules
                val allLocations = getAllLocationUseCase()
                val schedules = getScheduleForWeekUseCase()
                val listOfLocationUiModel = allLocations.map {
                    locationToLocationUiModelMapper.map(it)
                }

                //TODO it should be moved to mapper
                schedules.body()?.let { scheduleResponse ->
                    for (i in listOfLocationUiModel.indices) {
                        for (j in scheduleResponse.schedule.indices) {
                            if (listOfLocationUiModel[i].group - 1 == j) {
                                listOfLocationUiModel[i].schedule =
                                    scheduleResponse.schedule[j].map {
                                        scheduleToDayUiModelMapper.map(it)
                                    }
                                val remainingModel = parseTime(listOfLocationUiModel[i], i)
                                listOfLocationUiModel[i].remainingTime = remainingModel.time
                                listOfLocationUiModel[i].typeShutdown = remainingModel.typeShutdown
                            }
                        }
                    }
                }
                workManager.enqueue(list)
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
            deleteLocationUseCase(locationName)
        }
        getLocations()
    }

    private fun parseTime(location: LocationUiModel, indexId: Int): TimeModel {
        //TODO put parseTime to mapper
        val calendar = Calendar.getInstance(Locale.getDefault())
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK) - 2


        location.schedule?.get(day)?.daySchedule?.let { listOfSchedules ->
            for (i in listOfSchedules.indices) {
                val periodsOfTime = listOfSchedules[i].periodOfTime.split("-")

                val formatter = DateTimeFormat.forPattern("HH:mm")

                val currentTime = DateTime.now()
                val start = formatter.parseDateTime(periodsOfTime[0])
                    .withDate(currentTime.year, currentTime.monthOfYear, currentTime.dayOfMonth)
                val end = if (periodsOfTime[1] != "00:00") {
                    formatter.parseDateTime(periodsOfTime[1])
                        .withDate(currentTime.year, currentTime.monthOfYear, currentTime.dayOfMonth)
                } else {
                    formatter.parseDateTime("23:59")
                        .withDate(currentTime.year, currentTime.monthOfYear, currentTime.dayOfMonth)
                }

                if (currentTime.isBefore(end) && currentTime.isAfter(start)) {
                    val duration = Duration(currentTime, end)
                    val remainingHours = duration.standardHours.toInt()
                    val remainingMinutes = duration.standardMinutes % 60
                    val time = "${remainingHours}г ${remainingMinutes}хв"
                    if (duration.standardMinutes > 30) {
                        val delay = duration.standardMinutes - 30
                        createNotification(delay, location.name, indexId)
                    }

                    return TimeModel(time, listOfSchedules[i].typeShutdown)
                }
            }
        }
        return TimeModel("Error", null)
    }

    private fun clearWorkManager() {
        workManager.cancelAllWork()
    }

    private fun createNotification(delay: Long, locationName: String, indexId: Int) {

        val locationInfo = Data.Builder()
            .putString("name", locationName)
            .putLong(NotifyWork.NOTIFICATION_ID, indexId.toLong())
            .putLong("delay", delay)
            .build()

        val constraints =
            Constraints.Builder()
                .build()

        val workRequest =
            OneTimeWorkRequest
                .Builder(NotifyWork::class.java)
                .setInputData(locationInfo)
                .setInitialDelay(delay, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        list.add(workRequest)
    }
}
