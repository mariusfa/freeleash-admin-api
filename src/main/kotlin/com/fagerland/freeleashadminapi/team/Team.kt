package com.fagerland.freeleashadminapi.team

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Team(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val name: String
)