package com.example.whatseoul.controller.citydata;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.example.whatseoul.service.ApiScheduler;

@RestController
public class CityDataController {

	private final ApiScheduler apiScheduler;

	public CityDataController(ApiScheduler apiScheduler) {
		this.apiScheduler = apiScheduler;
	}

	@GetMapping("/")
	public String indexPage() {
		return "/index/index";
	}

	@GetMapping("/district")
	public String selectDistrictPage() {
		return "/selectarea/selectdistrict";
	}

	@GetMapping("/area")
	public String selectAreaNamePage() {
		return "/selectarea/selectareaname";
	}

	@GetMapping("/area/confirm")
	public String selectAreaNameConfirmPage() {
		return "/selectarea/selectconfirm";
	}

	// 핫스팟 전체 도시데이터 조회
	// @GetMapping("/citydata")
	// public String fetchCityData() {
	// 	apiScheduler.call();
	// 	return "/citydata/citydatalist";
	// }

	// 개별 핫스팟 도시데이터 조회
	// @GetMapping("/api/citydata/{areaName}")
	// public ResponseEntity<CityData> getCityDataByAreaName(@PathVariable String areaName) {
	// 	CityData cityData = cityDataService.getCityDataByAreaName(areaName);
	// 	return ResponseEntity.ok(cityData);
	// }
}
