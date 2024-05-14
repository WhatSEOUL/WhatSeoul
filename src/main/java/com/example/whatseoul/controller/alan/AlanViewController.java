package com.example.whatseoul.controller.alan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlanViewController {

	// 앨런 기본질의 답변 조회 html 간단 연동
	@GetMapping("/alan/basic")
	public String alanPage() {
		return "alan/alan";
	}

	// 앨런 sse질의 답변 조회 html 간단 연동
	@GetMapping("/alan/sse")
	public String alanSsePage() {
		return "alan/alansse";
	}
}
