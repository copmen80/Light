package com.electro.light.location.createlocation.choosegroup.ui

import androidx.lifecycle.ViewModel
import com.electro.light.location.createlocation.choosegroup.domain.GetGroupsUseCase

class GroupsViewModel(private val getGroupsUseCase: GetGroupsUseCase) : ViewModel() {

    fun getGroups() = getGroupsUseCase.invoke()
}