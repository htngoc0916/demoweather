package com.example.weather.mvc.services;

import com.example.weather.mvc.dto.WeatherDTO;
import com.example.weather.mvc.entity.Weather;

import java.util.List;

public interface WeatherService {
    List<Weather> getWeatherList(int page, int items);
    List<Weather> findByAll();
    Weather insert(Weather weather);
    void insertWeather(WeatherDTO weatherDTO);
    void updateWeather(WeatherDTO weatherDTO);
}
