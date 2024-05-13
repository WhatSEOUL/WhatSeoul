package com.example.whatseoul.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "area")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "AREA_CODE", unique = true)
    private String areaCode;

    @Column(name = "AREA_NAME")
    private String areaName;

    @Column(name = "AREA_LATITUDE")
    private Double areaLatitude;

    @Column(name = "AREA_LONGITUDE")
    private Double areaLongitude;

    @Column(name = "AREA_LOCATION_INFO", columnDefinition = "TEXT")
    private String areaLocationInfo;

    @Column(name = "AREA_ATTRACTION_INFO", columnDefinition = "TEXT")
    private String areaAttractionInfo;

    @Column(name = "AREA_ADDRESS")
    private String areaAddress;
}