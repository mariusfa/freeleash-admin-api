package com.fagerland.freeleashadminapi.toggle.domain

import com.fagerland.freeleashadminapi.toggle.dto.ToggleConstraintDTO
import javax.persistence.Embeddable

@Embeddable
class ToggleConstraint(
    var field: String
) {
    fun toDTO(): ToggleConstraintDTO = ToggleConstraintDTO(field = this.field)
}