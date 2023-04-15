package com.electro.light.location.common.data.remote

import com.electro.light.location.common.data.response.ScheduleResponse
import com.electro.light.location.common.data.response.ScheduleResponseByGroup
import retrofit2.Response
import retrofit2.http.*

interface LightApi {
    @GET("/get_schedule")
    suspend fun getScheduleForWeek(): Response<ScheduleResponse>

    @GET("/get_schedule")
    suspend fun getScheduleForWeekByGroupId(
        @Query("groupId") groupId: Int
    ): Response<ScheduleResponseByGroup>
}