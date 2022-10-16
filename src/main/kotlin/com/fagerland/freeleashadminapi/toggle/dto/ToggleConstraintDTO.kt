package com.fagerland.freeleashadminapi.toggle.dto

import com.fagerland.freeleashadminapi.toggle.domain.ToggleConstraint

data class ToggleConstraintDTO(
    val field: String
) {
    fun toDomain(): ToggleConstraint = ToggleConstraint(field = this.field)
}
