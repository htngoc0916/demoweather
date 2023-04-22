package com.example.weather.mvc.mapper;


import com.example.weather.mvc.dto.WeatherDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface WeatherMapper {
    void insertWeather(WeatherDTO boardDTO);
    void updateWeather(WeatherDTO boardDTO);
}
