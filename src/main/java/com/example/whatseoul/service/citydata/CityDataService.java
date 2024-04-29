package com.example.whatseoul.service.citydata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class CityDataService {
	@Autowired
	private RestTemplate restTemplate;

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

}
