package com.electro.light.location.explore.data.remote

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface LightApi {
    @GET("/mob-app/qpc/subject/")
    fun getSubjects(): Call<JSONObject>

    @FormUrlEncoded
    @POST("/api-route/edit-order")
    fun setOrderStatus(
        @Field("order_id") id: Int,
        @Field("new_status") status: String,
    ): Call<JSONObject>
}