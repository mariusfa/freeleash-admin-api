package com.fagerland.freeleashadminapi.toggle.biz.repository.jpa

import com.fagerland.freeleashadminapi.toggle.rest.dto.ConditionDTO
import javax.persistence.CollectionTable
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "Condition")
@Table(name = "condition")
class ConditionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var field: String,
    var operator: ConditionOperatorEntity,
    @ElementCollection()
    @CollectionTable(name = "content", foreignKey = ForeignKey(name = "FK_condition"))
    var contents: Set<String>
) {
    fun toDTO(): ConditionDTO =
        ConditionDTO(field = this.field, contents = this.contents, operator = this.operator.toDTO())
}