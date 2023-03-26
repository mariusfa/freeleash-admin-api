package com.fagerland.freeleashadminapi.toggle.biz.repository.jpa

import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamEntity
import com.fagerland.freeleashadminapi.toggle.rest.dto.ToggleDTO
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity(name = "Toggle")
@Table(name = "toggle")
class ToggleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String,
    @ManyToOne
    @JoinColumn(foreignKey = ForeignKey(name = "FK_team"))
    var team: TeamEntity,
    var isToggled: Boolean,
    var operator: ToggleOperatorEntity = ToggleOperatorEntity.AND,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "toggle_id", foreignKey = ForeignKey(name = "FK_toggle"))
    var conditions: MutableSet<ConditionEntity> = mutableSetOf()
) {
    fun toDTO(): ToggleDTO =
        ToggleDTO(
            id = this.id!!,
            name = this.name,
            isToggled = this.isToggled,
            operator = this.operator.toDTO(),
            conditions = this.conditions.map { it.toDTO() }.toSet()
        )
}

