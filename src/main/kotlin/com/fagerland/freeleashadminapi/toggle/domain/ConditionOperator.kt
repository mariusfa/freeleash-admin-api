package com.fagerland.freeleashadminapi.toggle.domain

import com.fagerland.freeleashadminapi.toggle.dto.ConditionOperatorDTO

enum class ConditionOperator {
    IN, LE;

    fun toDTO(): ConditionOperatorDTO = when (this) {
        IN -> ConditionOperatorDTO.IN
        LE -> ConditionOperatorDTO.LE
    }
}