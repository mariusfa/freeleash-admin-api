package com.fagerland.freeleashadminapi.toggle.rest.dto

import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ConditionEntity

data class ConditionDTO(
    val field: String,
    val contents: Set<String>,
    val operator: ConditionOperatorDTO
) {
    fun toDomain(): ConditionEntity =
        ConditionEntity(field = this.field, contents = this.contents, operator = this.operator.toDomain())
}
