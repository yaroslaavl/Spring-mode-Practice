package com.dmdev.spring.config;

import com.dmdev.spring.config.condition.JpaCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
@Slf4j
@Conditional(JpaCondition.class)
@Configuration
public class JpaConfig {

    /*@Bean
    @ConfigurationProperties(prefix = "db")
    public DataProperties dataProperties(){
        return new DataProperties();
    }*/

    @PostConstruct
    void init(){
        log.info("Jpa enabled");
    }
}
