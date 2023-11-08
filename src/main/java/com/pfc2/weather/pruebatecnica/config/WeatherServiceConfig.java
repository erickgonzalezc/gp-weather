package com.pfc2.weather.pruebatecnica.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource", ignoreInvalidFields = true)
public class WeatherServiceConfig {
    private String url;
    private String driveClassName;
    private String userName;
    private String password;
}
