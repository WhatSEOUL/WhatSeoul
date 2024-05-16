package com.example.whatseoul.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.whatseoul.dto.response.CultureEventDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.CultureEvent;
import com.example.whatseoul.repository.cityData.AreaRepository;
import com.example.whatseoul.repository.cityData.CulturalEventRepository;

@ExtendWith(MockitoExtension.class)
public class CultureEventServiceTest {
	@InjectMocks
	CultureEventService cultureEventService;

	@Mock
	AreaRepository areaRepository;

	@Mock
	CulturalEventRepository culturalEventRepository;

	@DisplayName("문화행사데이터 조회 테스트")
	@Test
	public void testGetCultureEventData() {

		// given
		Area area = new Area();
		String areaCode = "POI002";
		CultureEvent cultureEvent = CultureEvent.builder()
			.culturalEventName("[DDP] Greencanvas in ddp 전시")
			.culturalEventPeriod("2024-05-13~2024-05-29")
			.culturalEventPlace("동대문디자인플라자(DDP) 뮤지엄 3층 둘레길갤러리")
			.culturalEventUrl("https://culture.seoul.go.kr/culture/culture/cultureEvent/view.do?cultcode=146793&menuNo=200009")
			.area(area)
			.build();

		List<CultureEvent> cultureEventList = new ArrayList<>();
		cultureEventList.add(cultureEvent);
		when(areaRepository.findAreaByAreaCode(areaCode)).thenReturn(Optional.of(area));
		when(culturalEventRepository.findCultureEventsByArea(area)).thenReturn(cultureEventList);

		// when
		List<CultureEventDto> cultureEventDtos = cultureEventService.getCultureEventData(areaCode);

		// then
		assertNotNull(cultureEventDtos);
		assertEquals(1, cultureEventDtos.size());
		CultureEventDto dto = cultureEventDtos.get(0);
		assertEquals("[DDP] Greencanvas in ddp 전시", dto.getCulturalEventName());
		assertEquals("2024-05-13~2024-05-29", dto.getCulturalEventPeriod());
		assertEquals("동대문디자인플라자(DDP) 뮤지엄 3층 둘레길갤러리", dto.getCulturalEventPlace());
		assertEquals("https://culture.seoul.go.kr/culture/culture/cultureEvent/view.do?cultcode=146793&menuNo=200009", dto.getCulturalEventUrl());
	}
}
