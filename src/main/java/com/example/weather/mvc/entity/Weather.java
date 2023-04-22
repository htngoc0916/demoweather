package com.example.weather.mvc.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "WEATHER")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;
    @Column(name = "AREA", nullable = false)
    private String area;
    @Column(name = "ICON")
    private Integer icon;
    @Column(name = "TEMP")
    private Double temp;
    @Column(name = "REG_DT")
    private Date regDt;
}
