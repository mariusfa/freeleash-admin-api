package com.fagerland.freeleashadminapi.toggle.dto

import com.fagerland.freeleashadminapi.toggle.domain.ToggleConstraint

data class ToggleDTO(
    val id: Long,
    val name: String,
    val isToggled: Boolean,
    val toggleOperator: ToggleOperatorDTO,
    val toggleConstraints: List<ToggleConstraintDTO>
)


