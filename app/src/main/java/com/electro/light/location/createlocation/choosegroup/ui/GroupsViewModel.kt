package com.electro.light.location.createlocation.choosegroup.ui

import androidx.lifecycle.ViewModel
import com.electro.light.location.createlocation.choosegroup.ui.model.GroupUiModel

class GroupsViewModel : ViewModel() {

    fun getGroups() = arrayListOf(
        GroupUiModel(1),
        GroupUiModel(2),
        GroupUiModel(3),
    )
}