package com.electro.light.location.detailed.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleUiModel(
    val icon: Int?,
    val periodOfTime: String,
    val remainingTime: String,
    val typeShutdown: Int
) : Parcelable
