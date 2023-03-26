package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamEntity
import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamRepositoryJpa
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@ActiveProfiles("test")
@Testcontainers
class TeamEntityRepositoryTest {

    @Autowired
    private lateinit var teamRepositoryJpa: TeamRepositoryJpa

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should inject components`() {
        assertThat(teamRepositoryJpa).isNotNull
        assertThat(jdbcTemplate).isNotNull
    }

    @Test
    fun `should be able to create team`() {
        teamRepositoryJpa.save(TeamEntity(name = "testing"))
        val listOfTeams = jdbcTemplate.queryForList("select * from team")
        assertThat(listOfTeams).hasSize(1)
        val teamFound = listOfTeams[0]
        assertThat(teamFound["NAME"]).isEqualTo("testing")
    }
}
