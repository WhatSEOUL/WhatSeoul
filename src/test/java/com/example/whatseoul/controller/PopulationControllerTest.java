package com.example.whatseoul.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.whatseoul.dto.response.PopulationDataDto;
import com.example.whatseoul.entity.Population;
import com.example.whatseoul.service.PopulationService;

@ExtendWith(MockitoExtension.class)
public class PopulationControllerTest {
	@InjectMocks
	PopulationController populationController;

	@Mock
	PopulationService populationService;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(populationController).build();
	}

	@DisplayName("인구데이터 조회")
	@Test
	public void testGetPopulationData() throws Exception {
		// given
		final String url = "/api/ppltn/{areaName}";
		final String areaName = "강남 MICE 관광특구";

		// when
		ResultActions result = mockMvc.perform(get(url));

		// then
		PopulationDataDto populationData = populationService.getPopulationData(areaName);
		result.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].areaCongestionLevel").value(populationData.getAreaCongestionLevel()));

	}

}
