package com.example.whatseoul.dto;

import java.util.List;

import com.example.whatseoul.entity.Population;
import com.example.whatseoul.entity.PopulationForecast;
import com.example.whatseoul.entity.Weather;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CityData {
    private Weather weather;
    private Population population;
    private List<PopulationForecast> pplForecast;
}
