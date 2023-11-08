package com.pfc2.weather.pruebatecnica.controller;

import com.pfc2.weather.pruebatecnica.model.WeatherHistoryResponse;
import com.pfc2.weather.pruebatecnica.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WeatherController {
    private final WeatherService weatherService;
    @PostMapping("/weather/{lat}/{lon}")
    @ResponseStatus(HttpStatus.OK)
    public WeatherHistoryResponse getWeatherInfoByCord(@PathVariable("lat") Double lat, @PathVariable("lon") Double lon){
        return weatherService.getWeatherWithParams(lat, lon);
    }
}
