package com.example.weather.mvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class WeatherDTO {
    private static final long serialVersionUID = 1L;

    public static final Type TYPE = new TypeReference<List<WeatherDTO>>() {
    }.getType();

    @ApiModelProperty("일련번호")
    private Long seq;
    @ApiModelProperty("지역")
    private String area;
    @ApiModelProperty("아이콘")
    private Integer icon;
    @ApiModelProperty("온도")
    private Double temp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty("등록일")
    private Date regDt = new Date();
}
