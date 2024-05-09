package com.example.whatseoul.repository.cityData;

import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.CultureEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CulturalEventRepository extends JpaRepository<CultureEvent, Long> {
    List<CultureEvent> findCultureEventsByArea(Area area);
}