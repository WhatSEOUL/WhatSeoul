package com.example.whatseoul.controller.citydata;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.xml.sax.SAXException;

import com.example.whatseoul.entity.CityData;
import com.example.whatseoul.service.citydata.ApiScheduler;
import com.example.whatseoul.service.citydata.CityDataService;

@Controller
public class CityDataController {

	private final CityDataService cityDataService;
	private final ApiScheduler apiScheduler;

	public CityDataController(CityDataService cityDataService, ApiScheduler apiScheduler) {
		this.cityDataService = cityDataService;
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

	@GetMapping("/citydata")
	public String fetchCityData() {
		apiScheduler.call();
		return "/citydata/citydatalist";
	}

	@GetMapping("/api/citydata/{areaName}")
	public ResponseEntity<CityData> getCityDataByAreaName(@PathVariable String areaName) {
		CityData cityData = cityDataService.getCityDataByAreaName(areaName);
		return ResponseEntity.ok(cityData);
	}
}
