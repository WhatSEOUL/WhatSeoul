package com.example.whatseoul.repository.cityData;

import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findWeatherByArea(Area area);
}
