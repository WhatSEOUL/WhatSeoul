package com.example.whatseoul.respository.cityData;

import com.example.whatseoul.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
