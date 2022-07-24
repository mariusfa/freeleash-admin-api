package com.fagerland.freeleashadminapi

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TeamTest(@Autowired private val mvc: MockMvc) {

    @Test
    fun `should test list teams`() {
        mvc.perform(
            get("/team")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(
                content().string(
                    """
                    TODO
                    """.trimIndent()
                )
            )
    }

    @Test
    fun `should test create team`() {
        mvc.perform(
            post("/team")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(
                content().string(
                    """
                    TODO
                    """.trimIndent()
                )
            )
    }

    @Test
    fun `should test update team`() {
        mvc.perform(
            put("/team")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(
                content().string(
                    """
                    TODO
                    """.trimIndent()
                )
            )
    }

    @Test
    fun `should test delete team`() {
        mvc.perform(
            delete("/team")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(
                content().string(
                    """
                    TODO
                    """.trimIndent()
                )
            )
    }
}