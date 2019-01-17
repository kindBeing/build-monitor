package io.pivotal.bm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.bm.domain.PRWebRepository;
import io.pivotal.bm.domain.RepoWebRepository;
import io.pivotal.bm.services.PRWebService;
import io.pivotal.bm.services.RepoWebService;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
    }

    @Bean
    public RepoWebRepository repoWebRepository(RestTemplateBuilder restTemplateBuilder){
        return new RepoWebService(restTemplateBuilder);
    }

    @Bean
    public PRWebRepository prWebRepository(RestTemplateBuilder restTemplateBuilder){
        return new PRWebService(restTemplateBuilder);
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
