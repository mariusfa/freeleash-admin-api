package com.fagerland.freeleashadminapi.toggle.rest.dto

import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ConditionOperatorEntity

enum class ConditionOperatorDTO {
    IN, LE;

    fun toDomain(): ConditionOperatorEntity = when (this) {
        IN -> ConditionOperatorEntity.IN
        LE -> ConditionOperatorEntity.LE
    }
}