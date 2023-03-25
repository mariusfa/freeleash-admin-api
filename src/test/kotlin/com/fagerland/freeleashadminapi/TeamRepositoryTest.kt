package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.Team
import com.fagerland.freeleashadminapi.team.TeamRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.jdbc.core.JdbcTemplate

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private lateinit var teamRepository: TeamRepository

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should inject components`() {
        assertThat(teamRepository).isNotNull
        assertThat(jdbcTemplate).isNotNull
    }

    @Test
    fun `should be able to create team`() {
        teamRepository.save(Team(name = "testing"))
        val listOfTeams = jdbcTemplate.queryForList("select * from team")
        assertThat(listOfTeams).hasSize(1)
        val teamFound = listOfTeams[0]
        assertThat(teamFound["NAME"]).isEqualTo("testing")
    }
}
