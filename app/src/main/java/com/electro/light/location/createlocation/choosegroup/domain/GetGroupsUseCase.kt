package com.electro.light.location.createlocation.choosegroup.domain

import com.electro.light.location.createlocation.choosegroup.ui.model.GroupUiModel

class GetGroupsUseCase {

    operator fun invoke(): ArrayList<GroupUiModel> {
        return getGroups()
    }

    private fun getGroups() = arrayListOf(
        GroupUiModel(1),
        GroupUiModel(2),
        GroupUiModel(3),
    )
}