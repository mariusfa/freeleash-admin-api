package com.fagerland.freeleashadminapi.toggle.dto

import com.fagerland.freeleashadminapi.toggle.domain.ConditionOperator

enum class ConditionOperatorDTO {
    IN, LE;

    fun toDomain(): ConditionOperator = when (this) {
        IN -> ConditionOperator.IN
        LE -> ConditionOperator.LE
    }
}