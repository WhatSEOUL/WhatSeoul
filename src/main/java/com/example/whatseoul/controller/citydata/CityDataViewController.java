package com.example.whatseoul.controller.citydata;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.whatseoul.security.CustomUserDetails;
import com.example.whatseoul.service.ApiScheduler;
import com.example.whatseoul.service.account.AccountService;

@Controller
public class CityDataViewController {

	private final ApiScheduler apiScheduler;

	public CityDataViewController(ApiScheduler apiScheduler, AccountService accountService) {
		this.apiScheduler = apiScheduler;
	}

	@GetMapping("/")
	public String indexPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		if (userDetails != null) {
			model.addAttribute("isAuthenticated", true);
		} else {
			model.addAttribute("isAuthenticated", false);
		}
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

	// 유저가 지역구 선택한 후 "네, 좋아요" 버튼 클릭했을 때 도시데이터를 조회하는 화면
	@GetMapping("/citydata")
	public String citydataPage() {
		return "/citydata/citydatalist";
	}
}
