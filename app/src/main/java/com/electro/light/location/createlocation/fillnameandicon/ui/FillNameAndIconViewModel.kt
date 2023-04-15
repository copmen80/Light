package com.electro.light.location.createlocation.fillnameandicon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.electro.light.R
import com.electro.light.location.common.data.Location
import com.electro.light.location.common.data.LocationRepository
import com.electro.light.location.createlocation.fillnameandicon.ui.model.IconUiModel
import kotlinx.coroutines.launch

class FillNameAndIconViewModel(
    private val locationRepository: LocationRepository
) : ViewModel() {

    fun addLocation(location: Location) {
        viewModelScope.launch {
            try {
                locationRepository.addLocationToDb(location)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getIcons() = arrayListOf(
        IconUiModel(R.drawable.ic_location, true),
        IconUiModel(R.drawable.ic_email, false),
        IconUiModel(R.drawable.ic_send, false)
    )
}

