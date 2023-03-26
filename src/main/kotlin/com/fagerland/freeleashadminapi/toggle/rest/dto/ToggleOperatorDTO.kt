package com.fagerland.freeleashadminapi.toggle.rest.dto

import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleOperatorEntity

enum class ToggleOperatorDTO {
    AND, OR;

    fun toDomain(): ToggleOperatorEntity = when (this) {
        AND -> ToggleOperatorEntity.AND
        OR -> ToggleOperatorEntity.OR
    }
}