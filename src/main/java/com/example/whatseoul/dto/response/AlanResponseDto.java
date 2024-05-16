package com.example.whatseoul.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AlanResponseDto {
	@JsonProperty(value = "type")
	private String type;

	@JsonProperty(value = "data")
	private Data data;

	@Getter
	public static class Data {
		@JsonProperty(value = "content")
		private String content;

		@JsonProperty(value = "name")
		private String name;

		@JsonProperty(value = "speak")
		private String speak;
	}
}