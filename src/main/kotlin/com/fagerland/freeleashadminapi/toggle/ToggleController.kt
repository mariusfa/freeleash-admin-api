package com.fagerland.freeleashadminapi.toggle

import com.fagerland.freeleashadminapi.toggle.dto.ToggleDTO
import com.fagerland.freeleashadminapi.toggle.dto.ToggleRequestDTO
import com.fagerland.freeleashadminapi.toggle.dto.UpdateToggleRequestDTO
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
) : ToggleContract {
    @GetMapping
    override fun getTogglesForTeam(@RequestParam team: String): List<ToggleDTO> =
        toggleService.listTogglesForTeam(team).map { it.toDTO() }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    override fun createToggle(@RequestBody toggleRequestDTO: ToggleRequestDTO) =
        toggleService.createToggle(toggleRequestDTO.toDomain())

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    override fun updateToggle(@PathVariable id: Long, @RequestBody updateToggleRequestDTO: UpdateToggleRequestDTO): ToggleDTO {
        val toggle = toggleService.updateToggle(updateToggleRequestDTO.toDomain(id))
        return toggle.toDTO()
    }

    @GetMapping("/{id}")
    override fun getToggle(@PathVariable id: Long): ToggleDTO {
        val toggle = toggleService.getToggle(id)
        return toggle.toDTO()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    override fun deleteToggle(@PathVariable id: Long) = toggleService.deleteToggle(id)
}