package com.example.whatseoul.controller.citydata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.xml.sax.SAXException;

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
	public String getCityData() throws IOException, ParserConfigurationException, SAXException {
		apiScheduler.call();
		return "/citydata/citydatalist";
	}
}
