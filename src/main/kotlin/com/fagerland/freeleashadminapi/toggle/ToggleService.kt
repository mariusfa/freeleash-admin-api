package com.fagerland.freeleashadminapi.toggle

import com.fagerland.freeleashadminapi.team.TeamRepository
import com.fagerland.freeleashadminapi.toggle.domain.Toggle
import com.fagerland.freeleashadminapi.toggle.domain.ToggleRequest
import com.fagerland.freeleashadminapi.toggle.domain.UpdateToggleRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ToggleService(
    private val toggleRepository: ToggleRepository,
    private val teamRepository: TeamRepository
) {
    fun createToggle(toggleRequest: ToggleRequest) {
        val team = teamRepository.findById(toggleRequest.teamId).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Team not found for id: ${toggleRequest.teamId}"
            )
        }
        if (toggleRepository.existsByNameAndTeamId(toggleRequest.name, toggleRequest.teamId)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Toggle with name: ${toggleRequest.name} already exists")
        }

        val newToggle = Toggle(name = toggleRequest.name, team = team, isToggled = toggleRequest.isToggled, toggleOperator = toggleRequest.toggleOperator)
        toggleRepository.save(newToggle)
    }

    fun listTogglesForTeam(teamName: String): List<Toggle> {
        if (!teamRepository.existsByName(teamName)) throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Team not found with name $teamName"
        )
        return toggleRepository.findAllByTeamName(teamName)
    }

    fun updateToggle(updateToggleRequest: UpdateToggleRequest): Toggle {
        val toggle = toggleRepository.findById(updateToggleRequest.id).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Toggle with id ${updateToggleRequest.id} not found"
            )
        }
        toggle.name = updateToggleRequest.name
        toggle.isToggled = updateToggleRequest.isToggled
        toggle.toggleOperator = updateToggleRequest.toggleOperator
        return toggleRepository.save(toggle)
    }

    fun getToggle(id: Long): Toggle = toggleRepository.findById(id).orElseThrow {
        ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Toggle with id $id not found"
        )
    }

    fun deleteToggle(id: Long) {
        if (!toggleRepository.existsById(id)) throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Toggle not found with id $id"
        )
        toggleRepository.deleteById(id)
    }
}