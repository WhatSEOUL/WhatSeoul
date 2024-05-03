package com.example.whatseoul.respository.cityData;

import com.example.whatseoul.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findAreaByAreaCode(String areaCode);
    Optional<Area> findAreaByAreaName(String areaName);
}
