package com.example.whatseoul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlanViewController {

	@GetMapping("/alan")
	public String alanPage() {
		return "/alan/alan";
	}
}
