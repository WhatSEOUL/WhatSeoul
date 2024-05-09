package com.example.whatseoul.controller.citydata;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.whatseoul.service.ApiScheduler;

@Controller
public class CityDataViewController {

	private final ApiScheduler apiScheduler;

	public CityDataViewController(ApiScheduler apiScheduler) {
		this.apiScheduler = apiScheduler;
	}

	@GetMapping("/")
	public String indexPage() {
		return "index/index";
	}

	@GetMapping("/district")
	public String selectDistrictPage() {
		return "selectarea/selectdistrict";
	}

	@GetMapping("/area")
	public String selectAreaNamePage() {
		return "selectarea/selectareaname";
	}

	@GetMapping("/area/confirm")
	public String selectAreaNameConfirmPage() {
		return "selectarea/selectconfirm";
	}

	// 유저가 지역구 선택한 후 "네, 좋아요" 버튼 클릭했을 때 도시데이터를 조회하는 화면
	@GetMapping("/citydata")
	public String citydataPage() {
		return "citydata/citydatalist";
	}
}
