package com.gbsoft.rainfallcollector.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> extends BasicResponse {
	
	private String resultCode;
	
	private String message;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private T data;
}
