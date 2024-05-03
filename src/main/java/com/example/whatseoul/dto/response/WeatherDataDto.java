package com.example.whatseoul.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class WeatherDataDto {
    private String temperature;
    private String maxTemp;
    private String minTemp;
    private String pm25Idx;
    private String pm10Idx;
    private String pcpMsg;
    private String updateTime;
    private String areaName;
}
