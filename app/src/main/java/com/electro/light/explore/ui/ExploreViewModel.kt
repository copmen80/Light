package com.electro.light.explore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.electro.light.explore.model.LightRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExploreViewModel(private val lightRepository: LightRepository) : ViewModel() {

    private val _flow = MutableSharedFlow<ExploreScreenState>()
    val flow: SharedFlow<ExploreScreenState>
        get() = _flow

    private fun addLocation() {
        viewModelScope.launch {

        }
    }
}