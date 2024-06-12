package com.gbsoft.rainfallcollector.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseBodyDto {
	private String resultCode;
	private String resultMsg;
	private Payload payload;

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	private static class Payload {
		private String resultData;
	}
}
