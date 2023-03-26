package com.fagerland.freeleashadminapi.toggle.biz.repository.jpa

import com.fagerland.freeleashadminapi.toggle.rest.dto.ConditionOperatorDTO

enum class ConditionOperatorEntity {
    IN, LE;

    fun toDTO(): ConditionOperatorDTO = when (this) {
        IN -> ConditionOperatorDTO.IN
        LE -> ConditionOperatorDTO.LE
    }
}