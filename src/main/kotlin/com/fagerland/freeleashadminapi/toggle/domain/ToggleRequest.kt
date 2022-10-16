package com.fagerland.freeleashadminapi.toggle.domain

data class ToggleRequest(
    val name: String,
    val teamId: Long,
    val isToggled: Boolean,
    val toggleOperator: ToggleOperator
)
