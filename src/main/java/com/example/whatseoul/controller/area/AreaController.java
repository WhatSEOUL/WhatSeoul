package com.example.whatseoul.controller.area;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.whatseoul.service.area.AreaService;

@Controller
public class AreaController {
	private AreaService areaService;

	public AreaController(AreaService areaService) {
		this.areaService = areaService;
	}

	@GetMapping("/areadata")
	public String insertAreaData() throws IOException {
		areaService.insertAreaData();
		return "/citydata/citydatalist";
	}

}