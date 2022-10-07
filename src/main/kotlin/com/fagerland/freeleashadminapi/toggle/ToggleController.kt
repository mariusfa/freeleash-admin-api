package com.fagerland.freeleashadminapi.toggle

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/toggle")
class ToggleController(
    private val toggleService: ToggleService
) {
    @GetMapping
    fun getTogglesForTeam(@RequestParam team: String): List<ToggleDTO> =
        toggleService.listTogglesForTeam(team).map { ToggleDTO(id = it.id!!, name = it.name, isToggled = it.isToggled) }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createToggle(@RequestBody toggleRequestDTO: ToggleRequestDTO) =
        toggleService.createToggle(toggleRequestDTO.toDomain())

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun updateToggle(@PathVariable id: Long, @RequestBody updateToggleRequestDTO: UpdateToggleRequestDTO): ToggleDTO {
        val toggle = toggleService.updateToggle(updateToggleRequestDTO.toDomain(id))
        return ToggleDTO(id = toggle.id!!, name = toggle.name, isToggled = toggle.isToggled)
    }

    @GetMapping("/{id}")
    fun getToggle(@PathVariable id: Long): ToggleDTO {
        val toggle =  toggleService.getToggle(id)
        return ToggleDTO(id = toggle.id!!, name = toggle.name, isToggled = toggle.isToggled)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteToggle(@PathVariable id: Long) = toggleService.deleteToggle(id)
}