package com.gbsoft.rainfallcollector.service.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthResponseBodyDto {
	private Token token;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public class Token {
		private String access_token;
		private Integer expires_in;
		private String token_type;
		private String scope;

	}
}


