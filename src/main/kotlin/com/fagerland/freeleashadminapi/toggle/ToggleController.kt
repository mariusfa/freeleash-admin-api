package com.fagerland.freeleashadminapi.toggle

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ToggleController {

    @GetMapping
    fun listToggles(): List<ToggleDTO> = listOf(ToggleDTO(1, "test", "test-team", true, 100))
}