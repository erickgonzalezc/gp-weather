package com.pfc2.weather.pruebatecnica.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {
    @Value("${service.weather.base-url}")
    private String wtBaseUrl;


    @Bean
    public WebClient wtServiceWebClient() {
        return WebClient.builder()
                .baseUrl(wtBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(48 * 1024 * 1024))
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create(getProvider())))
                .build();
    }
    private ConnectionProvider getProvider() {
        return ConnectionProvider.builder("statusRedesignConnectionProvider")
                .maxConnections(500)
                .maxIdleTime(Duration.ofSeconds(20))
                .maxLifeTime(Duration.ofSeconds(60))
                .pendingAcquireTimeout(Duration.ofSeconds(60))
                .evictInBackground(Duration.ofSeconds(120)).build();
    }
    @Bean
    public DefaultUriBuilderFactory defaultUriBuilderFactory() {
        return new DefaultUriBuilderFactory();
    }

}
