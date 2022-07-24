package com.fagerland.freeleashadminapi.team

import org.springframework.stereotype.Service

@Service
class TeamService(
    private val teamRepository: TeamRepository
) {
    fun findTeams(): MutableIterable<Team> = teamRepository.findAll()
}