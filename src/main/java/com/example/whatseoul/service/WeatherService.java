package com.example.whatseoul.service;

import com.example.whatseoul.dto.response.WeatherDataDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Weather;
import com.example.whatseoul.respository.cityData.AreaRepository;
import com.example.whatseoul.respository.cityData.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final AreaRepository areaRepository;

    public WeatherDataDto getWeatherData(String areaCode){

        Area area = areaRepository.findAreaByAreaCode(areaCode)
                .orElseThrow(()-> new NoSuchElementException("Area Not Found"));
        Weather weather = weatherRepository.findWeatherByArea(area)
                .orElseThrow(() -> new NoSuchElementException("Weather Not Found"));

        return WeatherDataDto.builder()
                .areaName(area.getAreaName())
                .maxTemp(weather.getMaxTemperature())
                .minTemp(weather.getMinTemperature())
                .pm25Idx(weather.getPm25Index())
                .pm10Idx(weather.getPm10Index())
                .temperature(weather.getTemperature())
                .updateTime(weather.getWeatherTime())
                .pcpMsg(weather.getPcpMsg())
                .build();
    }
}
