package com.fagerland.freeleashadminapi.toggle

import com.fagerland.freeleashadminapi.team.Team
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Toggle(
    @Id
    @GeneratedValue
    val id: Long? = null,
    var name: String,
    @ManyToOne
    val team: Team,
    var isToggled: Boolean
)