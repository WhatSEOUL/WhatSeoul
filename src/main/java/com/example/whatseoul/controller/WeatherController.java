package com.example.whatseoul.controller;

import com.example.whatseoul.dto.response.WeatherDataDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.respository.cityData.AreaRepository;
import com.example.whatseoul.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherController {

    private final AreaRepository areaRepository;
    private final WeatherService weatherService;

    @GetMapping("/weather/{areaName}")
    public ResponseEntity<WeatherDataDto> getWeatherData(@PathVariable String areaName){
        Area area = areaRepository.findAreaByAreaName(areaName)
                .orElseThrow(() -> new NoSuchElementException("Area Not Found"));
        WeatherDataDto dataDto = weatherService.getWeatherData(area.getAreaCode());

        return ResponseEntity.ok(dataDto);
    }
}
