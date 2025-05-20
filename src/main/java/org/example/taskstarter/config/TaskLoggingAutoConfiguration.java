package org.example.taskstarter.config;


import org.example.taskstarter.aspect.LoggingAspect;
import org.example.taskstarter.properties.LoggingProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class TaskLoggingAutoConfiguration {

    @Bean
    public LoggingAspect loggingAspect(LoggingProperties properties) {
        return new LoggingAspect(properties);
    }
}