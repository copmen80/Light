package com.electro.light.location.common.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("schedule")
    val schedule: List<List<Schedule>>,
)

data class ScheduleResponseByGroup(
    @SerializedName("schedule")
    val schedule: List<Schedule>,
)

data class Schedule(
    @SerializedName("dayOfWeek")
    val dayOfWeek: String,
    @SerializedName("shutdown")
    val shutdown: List<Shutdown>,
)

data class Shutdown(
    @SerializedName("order")
    val order: Int,
    @SerializedName("shutdownType")
    val shutdownType: Int,
    @SerializedName("range")
    val range: String,
    @SerializedName("quantity")
    val quantity: String,
)


