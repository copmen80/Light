package com.electro.light.explore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.electro.light.explore.data.local.Location
import com.electro.light.explore.domain.GetAllLocationsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val getAllLocationsUseCase: GetAllLocationsUseCase
) : ViewModel() {

    private val _content = MutableStateFlow<List<Location>>(emptyList())
    val content = _content.asStateFlow()

    private fun getAllLocation() {
        viewModelScope.launch {
            try {
//                _content.emit(getAllLocationsUseCase())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}