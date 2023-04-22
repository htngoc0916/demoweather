package com.example.weather.mvc.services;

import com.example.weather.mvc.dto.WeatherDTO;
import com.example.weather.mvc.entity.Weather;
import com.example.weather.mvc.mapper.WeatherMapper;
import com.example.weather.mvc.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private WeatherMapper weatherMapper;

    @Override
    public List<Weather> findByAll() {
        return weatherRepository.findAll();
    }

    @Override
    public void insertWeather(WeatherDTO weatherDTO) {
        weatherMapper.insertWeather(weatherDTO);
    }

    @Override
    public void updateWeather(WeatherDTO weatherDTO) {
        weatherMapper.updateWeather(weatherDTO);
    }

    @Override
    public Weather insert(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public List<Weather> getWeatherList(int page, int items) {
        Pageable pageable = PageRequest.of(page, items);
        Page<Weather> weatherPage = weatherRepository.findAll(pageable);
        return weatherPage.getContent();
    }
}
