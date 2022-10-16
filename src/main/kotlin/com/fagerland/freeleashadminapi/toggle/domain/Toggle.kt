package com.fagerland.freeleashadminapi.toggle.domain

import com.fagerland.freeleashadminapi.team.Team
import com.fagerland.freeleashadminapi.toggle.dto.ToggleDTO
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Toggle(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var name: String,
    @ManyToOne
    var team: Team,
    var isToggled: Boolean,
    var toggleOperator: ToggleOperator,
    @ElementCollection
    var toggleConstraints: List<ToggleConstraint> = emptyList()
) {
    fun toDTO(): ToggleDTO =
        ToggleDTO(
            id = this.id!!,
            name = this.name,
            isToggled = this.isToggled,
            toggleOperator = this.toggleOperator.toDTO()
        )
}

