package com.pfc2.weather.pruebatecnica.service.impl;

import com.pfc2.weather.pruebatecnica.adapter.WeatherAdapter;
import com.pfc2.weather.pruebatecnica.model.WeatherData;
import com.pfc2.weather.pruebatecnica.model.WeatherHistory;
import com.pfc2.weather.pruebatecnica.model.WeatherHistoryResponse;
import com.pfc2.weather.pruebatecnica.repository.WeatherHistoryRepository;
import com.pfc2.weather.pruebatecnica.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
   private final WeatherAdapter weatherAdapter;
   private final WeatherHistoryRepository weatherHistoryRepository;
    @Override
    public Mono<WeatherHistoryResponse> getWeatherWithParams(Double lat, Double lon) {
        Date tenMinutesAgo = new Date(System.currentTimeMillis() - 600_000);
        log.info("aasj");
        return weatherHistoryRepository.findByLatAndLon(lat, lon, tenMinutesAgo ).flatMap(res ->{
            if(null == res){
                return weatherAdapter.getInfoByCord(lat, lon).flatMap(weatherResponse ->{
                    WeatherData.Weather weatherDa = Arrays.stream(weatherResponse.getWeather()).findFirst().orElse(null);

                    WeatherHistory newWeatherHistory = WeatherHistory.builder().weather(null != weatherDa? weatherDa.getMain() : "").lon(lon).lat(lat)
                            .humidity(weatherResponse.getMain().getHumidity()).tempMin(weatherResponse.getMain().getTemp_min()).tempMax(weatherResponse.getMain()
                                    .getTemp_max()).build();

                    WeatherHistoryResponse weatherHistoryResponse = WeatherHistoryResponse.builder().build();
                    return Mono.just(weatherHistoryResponse);
                });
            } else{
                WeatherHistoryResponse weatherHistoryResponse = WeatherHistoryResponse.builder().build();
                return Mono.just(weatherHistoryResponse);
            }
        });

    }
}
