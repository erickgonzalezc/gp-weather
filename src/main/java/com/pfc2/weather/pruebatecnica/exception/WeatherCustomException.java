package com.pfc2.weather.pruebatecnica.exception;

public class WeatherCustomException extends RuntimeException {

    public WeatherCustomException(String message) {
        super(message);
    }

    public WeatherCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}