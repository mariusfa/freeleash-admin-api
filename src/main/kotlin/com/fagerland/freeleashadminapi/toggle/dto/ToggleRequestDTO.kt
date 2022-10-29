package com.fagerland.freeleashadminapi.toggle.dto

import com.fagerland.freeleashadminapi.toggle.domain.ToggleRequest

data class ToggleRequestDTO(
    val name: String,
    val teamId: Long,
    val isToggled: Boolean,
    val operator: ToggleOperatorDTO,
    val conditions: Set<ConditionDTO>
) {
    fun toDomain(): ToggleRequest = ToggleRequest(
        name = name,
        teamId = teamId,
        isToggled = isToggled,
        operator = operator.toDomain(),
        conditions = conditions.map { it.toDomain() }.toSet()
    )
}
