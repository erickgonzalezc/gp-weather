package com.pfc2.weather.pruebatecnica.adapter;

import com.pfc2.weather.pruebatecnica.config.WebClientConfiguration;
import com.pfc2.weather.pruebatecnica.model.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherAdapter {
    private final WebClient wtServiceWebClient;

    @Value("service.weather.base-url")
    private String uriWeather;
    @Value("service.weather.api-id")
    private String apiId;

    public Mono<WeatherData> getInfoByCord(Double lat, Double longi){
        return wtServiceWebClient.get().uri(uriBuilder -> uriBuilder
                .path(uriWeather)
                .queryParam("lat", lat)
                .queryParam("lon", longi)
                .queryParam("appid", apiId)
                .build())
                .retrieve()
                .bodyToMono(WeatherData.class)
                .onErrorResume(e ->{
                    log.error(e.getMessage());
                    return Mono.error(new WeatherStatusAdapterException(e.getMessage()));
                });

    }
    public static class WeatherStatusAdapterException extends RuntimeException {
        private WeatherStatusAdapterException(String log) {
            super(log, null);
        }
    }

}
