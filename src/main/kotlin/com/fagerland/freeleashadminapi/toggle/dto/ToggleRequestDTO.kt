package com.fagerland.freeleashadminapi.toggle.dto

import com.fagerland.freeleashadminapi.toggle.domain.ToggleRequest

data class ToggleRequestDTO(
    val name: String,
    val teamId: Long,
    val isToggled: Boolean,
    val toggleOperator: ToggleOperatorDTO
) {
    fun toDomain(): ToggleRequest = ToggleRequest(name = name, teamId = teamId, isToggled = isToggled, toggleOperator = toggleOperator.toDomain())
}
