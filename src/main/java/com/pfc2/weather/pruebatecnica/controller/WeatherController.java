package com.pfc2.weather.pruebatecnica.controller;

import com.pfc2.weather.pruebatecnica.model.WeatherHistoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class WeatherController {

    @PostMapping("/weather/{lat}/{lon}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<WeatherHistoryResponse> getWeatherInfoByCord(@PathVariable("lat") Long lat, @PathVariable("lon") Long lon){
        return Mono.empty();
    }
}
