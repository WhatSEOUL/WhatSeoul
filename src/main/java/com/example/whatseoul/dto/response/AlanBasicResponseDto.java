package com.example.whatseoul.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AlanBasicResponseDto {
	@JsonProperty(value = "action")
	private Action action;

	@JsonProperty(value = "content")
	private String content;

	@Getter
	public static class Action {
		@JsonProperty(value = "name")
		String name;

		@JsonProperty(value = "speak")
		String speak;
	}
}
