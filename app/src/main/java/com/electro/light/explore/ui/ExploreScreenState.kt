package com.electro.light.explore.ui

data class ExploreScreenState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = ExploreScreenState(Status.SUCCESS)
        val LOADING = ExploreScreenState(Status.RUNNING)
        fun error(msg: String?) = ExploreScreenState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}