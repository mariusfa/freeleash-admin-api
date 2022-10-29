package com.fagerland.freeleashadminapi.toggle.domain

import com.fagerland.freeleashadminapi.toggle.dto.ConditionDTO
import javax.persistence.CollectionTable
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Condition(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var field: String,
    var operator: ConditionOperator,
    @ElementCollection
    @CollectionTable(name = "content")
    var contents: Set<String>
) {
    fun toDTO(): ConditionDTO =
        ConditionDTO(field = this.field, contents = this.contents, operator = this.operator.toDTO())
}