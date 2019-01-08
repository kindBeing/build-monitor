package io.pivotal.bm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.pivotal.bm.git.PRManager;
import io.pivotal.bm.git.RepoManager;
import io.pivotal.bm.services.GitDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ApplicationConfig {
    @Bean
    public ObjectMapper jsonObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .modules(new JavaTimeModule())
                .build();
    }

    @Bean
    public URLProvider urlProvider(){
        return new URLProvider();
    }

    @Bean
    public RepoManager repoManager(BMRepository bmRepository, GitDataService gitDataService, URLProvider urlProvider){
        return new RepoManager(bmRepository, gitDataService, urlProvider);
    }

    @Bean
    public PRManager prManager(BMRepository bmRepository, GitDataService gitDataService, URLProvider urlProvider){
        return new PRManager(bmRepository, gitDataService, urlProvider);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }

}
