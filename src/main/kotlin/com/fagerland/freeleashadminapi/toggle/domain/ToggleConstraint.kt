package com.fagerland.freeleashadminapi.toggle.domain

import com.fagerland.freeleashadminapi.toggle.dto.ToggleConstraintDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class ToggleConstraint(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var field: String
) {
    fun toDTO(): ToggleConstraintDTO = ToggleConstraintDTO(field = this.field)
}