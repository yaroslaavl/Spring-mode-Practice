package com.dmdev.spring.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Value
@ConstructorBinding
@ConfigurationProperties(prefix = "db")
public class DataProperties {


    String username;

    String password;

    String driver;

    String url;

    String hosts;

    PoolProperties pool;

    List<PoolProperties> pools;

    Map<String, Object> properties;

    @Value
    public static class PoolProperties {
         Integer size;
         Integer timeout;
    }
}
