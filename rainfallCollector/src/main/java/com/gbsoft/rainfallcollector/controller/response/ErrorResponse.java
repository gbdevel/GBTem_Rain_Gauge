package com.gbsoft.rainfallcollector.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class ErrorResponse<T> extends BasicResponse {

	private String resultCode;
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public ErrorResponse(String resultCode, String message) {
		this.resultCode = resultCode;
		this.message = message;
	}

	public ErrorResponse(String resultCode, String message, T data) {
		this.resultCode = resultCode;
		this.message = message;
		this.data = data;
	}
}