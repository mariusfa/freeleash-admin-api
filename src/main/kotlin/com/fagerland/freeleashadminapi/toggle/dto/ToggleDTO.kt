package com.fagerland.freeleashadminapi.toggle.dto

data class ToggleDTO(
    val id: Long,
    val name: String,
    val isToggled: Boolean,
    val toggleOperator: ToggleOperatorDTO
)


