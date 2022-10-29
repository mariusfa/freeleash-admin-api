package com.fagerland.freeleashadminapi.toggle.dto

import com.fagerland.freeleashadminapi.toggle.domain.Condition

data class ConditionDTO(
    val field: String,
    val contents: Set<String>
) {
    fun toDomain(): Condition = Condition(field = this.field, contents = this.contents)
}
