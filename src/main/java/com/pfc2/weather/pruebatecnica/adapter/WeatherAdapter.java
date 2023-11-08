package com.pfc2.weather.pruebatecnica.adapter;

import com.pfc2.weather.pruebatecnica.exception.WeatherCustomException;
import com.pfc2.weather.pruebatecnica.model.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherAdapter {
    private final WebClient wtServiceWebClient;

    @Value("service.weather.base-url")
    private String uriWeather;
    @Value("service.weather.api-id")
    private String apiId;

    public WeatherData getInfoByCord(Double lat, Double longi){
        try
        {
            return wtServiceWebClient.get().uri(uriBuilder -> uriBuilder
                            .path(uriWeather)
                            .queryParam("lat", lat)
                            .queryParam("lon", longi)
                            .queryParam("appid", apiId)
                            .build())
                    .retrieve()
                    .bodyToMono(WeatherData.class)
                    .block();

        }catch (WebClientResponseException ex) {
            log.error(ex.getMessage());
            throw new WeatherCustomException("Error al hacer la solicitud");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new WeatherCustomException("Error inesperado al hacer la solicitud");
        }


    }


}
