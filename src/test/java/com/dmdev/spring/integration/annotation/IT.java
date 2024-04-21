package com.dmdev.spring.integration.annotation;

import com.dmdev.spring.integration.TestRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = TestRunner.class, properties = "spring.config.location=classpath:application-test.yml")

@ActiveProfiles("test")
@EntityScan(basePackages = "com.dmdev.spring.database.entity")
@Transactional
public @interface IT {
}
