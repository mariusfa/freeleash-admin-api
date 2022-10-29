package com.fagerland.freeleashadminapi.toggle.dto

import com.fagerland.freeleashadminapi.toggle.domain.UpdateToggleRequest

data class UpdateToggleRequestDTO(
    val name: String,
    val isToggled: Boolean,
    val operator: ToggleOperatorDTO,
    val conditions: Set<ConditionDTO>
) {
    fun toDomain(id: Long): UpdateToggleRequest = UpdateToggleRequest(
        id = id,
        name = name,
        isToggled = isToggled,
        operator = operator.toDomain(),
        conditions = conditions.map { it.toDomain() }.toSet()
    )
}
