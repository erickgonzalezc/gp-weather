package com.pfc2.weather.pruebatecnica.service;

import com.pfc2.weather.pruebatecnica.model.WeatherHistoryResponse;
import reactor.core.publisher.Mono;

public interface WeatherService {
    Mono<WeatherHistoryResponse> getWeatherWithParams(Double lat, Double longi);
}
