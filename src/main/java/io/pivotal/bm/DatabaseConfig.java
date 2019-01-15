package io.pivotal.bm;

import io.pivotal.bm.domain.PRDBRepository;
import io.pivotal.bm.domain.RepoDBRepository;
import io.pivotal.bm.services.PRDBService;
import io.pivotal.bm.services.RepoDBService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    PRDBRepository prDBRepository(DataSource dataSource) {
        return new PRDBService(dataSource);
    }

    @Bean
    RepoDBRepository repoDBRepository(DataSource dataSource) {
        return new RepoDBService(dataSource);
    }
}
