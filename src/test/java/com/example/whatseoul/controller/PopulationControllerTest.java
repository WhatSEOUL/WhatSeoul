package com.example.whatseoul.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.whatseoul.dto.response.PopulationDataDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Population;
import com.example.whatseoul.respository.cityData.AreaRepository;
import com.example.whatseoul.service.PopulationService;

@ExtendWith(MockitoExtension.class)
public class PopulationControllerTest {
	@InjectMocks
	PopulationController populationController;

	@Mock
	PopulationService populationService;

	@Mock
	AreaRepository areaRepository;

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

		Area area = Area.builder()
			.areaName(areaName)
			.build();

		given(areaRepository.findAreaByAreaName(areaName)).willReturn(Optional.of(area));

		PopulationDataDto populationDataDto = PopulationDataDto.builder()
			.areaCongestionLevel("약간 붐빔빔")
			.build();

		given(populationService.getPopulationData(area.getAreaCode())).willReturn(populationDataDto);

		// when
		ResultActions result = mockMvc.perform(get(url, areaName));
		MvcResult mvcResult = mockMvc.perform(get(url, areaName)).andReturn();
		String jsonResponse = mvcResult.getResponse().getContentAsString();

		// then
		System.out.println("JSON Response: " + jsonResponse);
		result.andExpect(status().isOk())
			.andExpect(jsonPath("$.areaCongestionLevel").value(populationDataDto.getAreaCongestionLevel()));

	}

}
