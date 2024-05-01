package com.example.whatseoul.dto;

import com.example.whatseoul.entity.CultureEvent;
import com.example.whatseoul.entity.Population;
import com.example.whatseoul.entity.Weather;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CityData {
    private Weather weather;
    private Population population;
    private CultureEvent cultureEvent;
}
