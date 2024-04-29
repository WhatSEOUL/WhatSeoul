package com.example.whatseoul.controller.citydata;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.whatseoul.service.citydata.CityDataService;

@Controller
public class CityDataController {

	private final CityDataService cityDataService;

	public CityDataController(CityDataService cityDataService) {
		this.cityDataService = cityDataService;
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
}
