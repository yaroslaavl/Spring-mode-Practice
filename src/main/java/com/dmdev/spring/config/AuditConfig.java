package com.dmdev.spring.config;

import com.dmdev.spring.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = ApplicationRunner.class)
public class AuditConfig {

     @Bean
     public AuditorAware<String> AuditorAware(){
         //SecurityContext.getCurrentUser()
         return () -> Optional.of("dmdev");
     }
}
