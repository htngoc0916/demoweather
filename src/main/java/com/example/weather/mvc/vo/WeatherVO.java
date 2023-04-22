package com.example.weather.mvc.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class WeatherVO {
    private Long seq;
    private String area;
    private Integer icon;
    private Double temp;
    private Date regDt;
}
