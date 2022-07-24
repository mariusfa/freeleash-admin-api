package com.fagerland.freeleashadminapi.team

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TeamConfiguration {

    @Bean
    fun databaseInitializer(teamRepository: TeamRepository) = ApplicationRunner {
        teamRepository.save(Team(name = "default"))
    }
}