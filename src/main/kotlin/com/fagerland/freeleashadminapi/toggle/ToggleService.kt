package com.fagerland.freeleashadminapi.toggle

import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamRepository
import com.fagerland.freeleashadminapi.toggle.domain.Condition
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

        val newToggle = Toggle(
            name = toggleRequest.name,
            team = team,
            isToggled = toggleRequest.isToggled,
            operator = toggleRequest.operator,
            conditions = if (toggleRequest.conditions.isEmpty()) mutableSetOf() else toggleRequest.conditions as MutableSet<Condition>
        )
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
        if (toggleRepository.existsByNameAndTeamId(
                updateToggleRequest.name,
                toggle.team.id!!
            ) && updateToggleRequest.name != toggle.name
        ) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Toggle with name: ${updateToggleRequest.name} already exists"
            )
        }

        toggle.name = updateToggleRequest.name
        toggle.isToggled = updateToggleRequest.isToggled
        toggle.operator = updateToggleRequest.operator
        toggle.conditions.clear()
        toggle.conditions.addAll(updateToggleRequest.conditions)
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