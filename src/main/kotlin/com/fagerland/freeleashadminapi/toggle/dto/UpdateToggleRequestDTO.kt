package com.fagerland.freeleashadminapi.toggle.dto

import com.fagerland.freeleashadminapi.toggle.domain.UpdateToggleRequest

data class UpdateToggleRequestDTO(
    val name: String,
    val isToggled: Boolean,
    val toggleOperator: ToggleOperatorDTO,
    val toggleConstraints: List<ToggleConstraintDTO>
) {
    fun toDomain(id: Long): UpdateToggleRequest = UpdateToggleRequest(
        id = id,
        name = name,
        isToggled = isToggled,
        toggleOperator = toggleOperator.toDomain(),
        toggleConstraints = toggleConstraints.map { it.toDomain() }
    )
}
