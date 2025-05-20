package org.example.taskstarter.config;


import org.example.taskstarter.aspect.LoggingAspect;
import org.example.taskstarter.properties.LoggingProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class TaskLoggingAutoConfiguration {

    private final LoggingProperties loggingProperties;

    public TaskLoggingAutoConfiguration(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    @Bean
    @ConditionalOnProperty(name = "logging.aspect.enabled", havingValue = "true", matchIfMissing = true)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect(loggingProperties);
    }
}