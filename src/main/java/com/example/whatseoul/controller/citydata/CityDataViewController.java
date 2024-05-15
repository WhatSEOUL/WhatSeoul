package com.example.whatseoul.controller.citydata;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CityDataViewController {
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

	@GetMapping("/")
	public String indexPage(Model model, HttpSession session) {

		//로그인 성공 메시지
		Object loginSuccess=session.getAttribute("loginSuccess");
		if(loginSuccess!=null&& (Boolean)loginSuccess){
			model.addAttribute("loginSuccess", true);
			session.removeAttribute("loginSuccess");
		}

		//로그아웃 메시지
		Object logoutSuccess=session.getAttribute("logoutSuccess");
		if(logoutSuccess!=null&& (Boolean)logoutSuccess){
			model.addAttribute("logoutSuccess", true);
			session.removeAttribute("logoutSuccess");
		}

		return "index/index";
	}
}
