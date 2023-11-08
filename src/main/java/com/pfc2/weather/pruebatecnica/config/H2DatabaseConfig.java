package com.pfc2.weather.pruebatecnica.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class H2DatabaseConfig {
    private final WeatherServiceConfig weatherServiceConfig;
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(weatherServiceConfig.getDriveClassName());
        dataSource.setUrl(weatherServiceConfig.getUrl());
        dataSource.setUsername(weatherServiceConfig.getUserName());
        dataSource.setPassword(weatherServiceConfig.getPassword());
        return dataSource;
    }
}
