package com.fagerland.freeleashadminapi.toggle.domain

data class UpdateToggleRequest(
    val id: Long,
    val name: String,
    val isToggled: Boolean,
    val toggleOperator: ToggleOperator
)
