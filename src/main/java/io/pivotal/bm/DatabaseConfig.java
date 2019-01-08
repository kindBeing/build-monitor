package io.pivotal.bm;

import io.pivotal.bm.services.SQLDatabaseService;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Profile("local")
    @Bean
    BMRepository bmRepository() {
        return new InMemoryService();
    }

    @Profile("!local")
    @Bean
    BMRepository bmRepository(DataSource sqDataSource) {
        return new SQLDatabaseService(sqDataSource);
    }
}
