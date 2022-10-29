package com.fagerland.freeleashadminapi.toggle.domain

import com.fagerland.freeleashadminapi.toggle.dto.ConditionDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Condition(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var field: String
) {
    fun toDTO(): ConditionDTO = ConditionDTO(field = this.field)
}