package com.example.whatseoul.service.citydata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.whatseoul.entity.CityData;
import com.example.whatseoul.repository.citydata.CityDataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityDataService {
	private final CityDataRepository cityDataRepository;

	// @Autowired
	// private RestTemplate restTemplate;

	// @Transactional
	// @Scheduled()
	// public void saveCityData() {
	//
	// }
	// public void findCityData() {
	// 	String url = "http://openapi.seoul.go.kr:8088/52686c4c4267686934314d63466562/xml/citydata/1/5/%EA%B4%91%ED%99%94%EB%AC%B8%C2%B7%EB%8D%95%EC%88%98%EA%B6%81";
	// 	ResponseEntity response = restTemplate.getForEntity(url, String.class);
	// 	System.out.println("here");
	// }

	public CityData getCityDataByAreaName(String areaName) {
		CityData data = cityDataRepository.findByAreaName(areaName);
		log.info("getCityDataByAreaName - {}", data);
		log.info("getCityDataByAreaName - {}", data.getAreaName());

		return cityDataRepository.findByAreaName(areaName);
	}

}
