package com.fagerland.freeleashadminapi.toggle.domain

import com.fagerland.freeleashadminapi.team.Team
import com.fagerland.freeleashadminapi.toggle.dto.ToggleDTO
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Toggle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String,
    @ManyToOne
    @JoinColumn(foreignKey = ForeignKey(name = "FK_team"))
    var team: Team,
    var isToggled: Boolean,
    var operator: ToggleOperator,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "toggle_id", foreignKey = ForeignKey(name = "FK_toggle"))
    var conditions: MutableSet<Condition> = mutableSetOf()
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

