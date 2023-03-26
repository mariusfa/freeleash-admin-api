package com.fagerland.freeleashadminapi.toggle.rest.dto

import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ConditionOperator

enum class ConditionOperatorDTO {
    IN, LE;

    fun toDomain(): ConditionOperator = when (this) {
        IN -> ConditionOperator.IN
        LE -> ConditionOperator.LE
    }
}