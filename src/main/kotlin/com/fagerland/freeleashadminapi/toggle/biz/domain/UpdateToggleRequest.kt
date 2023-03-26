package com.fagerland.freeleashadminapi.toggle.biz.domain

import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ConditionEntity
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleOperatorEntity

data class UpdateToggleRequest(
    val id: Long,
    val name: String,
    val isToggled: Boolean,
    val operator: ToggleOperatorEntity,
    val conditions: Set<ConditionEntity>
)
