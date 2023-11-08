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
import java.util.Arrays;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
   private final WeatherAdapter weatherAdapter;
   private final WeatherHistoryRepository weatherHistoryRepository;
    @Override
    public WeatherHistoryResponse getWeatherWithParams(Double lat, Double lon) {
        Date tenMinutesAgo = new Date(System.currentTimeMillis() - 600_000);
        log.info("aasj");
        var  weatherHistoryOptional = weatherHistoryRepository.findByLatAndLon(lat, lon, tenMinutesAgo );

        if(weatherHistoryOptional.isPresent()){
            WeatherHistory weatherHistory = weatherHistoryOptional.get();
            return WeatherHistoryResponse.builder().weather(weatherHistory.getWeather()).humidity(weatherHistory.getHumidity()).tempMin(weatherHistory.getTempMin()).tempMax(weatherHistory.getTempMax()).build();
        }else{
            var weatherApiResponse = weatherAdapter.getInfoByCord(lat, lon);

            WeatherData.Weather weather = weatherApiResponse.getWeather()[0];

            WeatherHistory newWeatherHistory = WeatherHistory.builder().weather(weather.getMain()).lon(lon).lat(lat)
                    .humidity(weatherApiResponse.getMain().getHumidity()).tempMin(weatherApiResponse.getMain().getTemp_min()).tempMax(weatherApiResponse.getMain()
                            .getTemp_max()).build();
            weatherHistoryRepository.save(newWeatherHistory);
            return WeatherHistoryResponse.builder().weather(newWeatherHistory.getWeather()).humidity(newWeatherHistory.getHumidity()).tempMin(newWeatherHistory.getTempMin()).tempMax(newWeatherHistory.getTempMax()).build();
        }

    }
}
