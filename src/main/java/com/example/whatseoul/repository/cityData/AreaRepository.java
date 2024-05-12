package com.example.whatseoul.repository.cityData;

import com.example.whatseoul.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findAreaByAreaCode(String areaCode);
    Optional<Area> findAreaByAreaName(String areaName);

    @Query("SELECT a.areaName FROM Area a")
    List<String> findAllAreaNames();

    @Transactional
    @Modifying
    @Query("UPDATE Area a SET a.areaLocationInfo = :areaLocationInfo WHERE a.areaName = :areaName")
    void updateAreaLocationInfoByAreaName(String areaName, String areaLocationInfo);

    @Transactional
    @Modifying
    @Query("UPDATE Area a SET a.areaAttractionInfo = :areaAttractionInfo WHERE a.areaName = :areaName")
    void updateAreaAttractionInfoByAreaName(String areaName, String areaAttractionInfo);
}
