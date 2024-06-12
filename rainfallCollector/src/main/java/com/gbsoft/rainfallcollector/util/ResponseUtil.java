package com.gbsoft.rainfallcollector.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.gbsoft.rainfallcollector.controller.response.CommonResponse;

public class ResponseUtil<T> {

	public static ResponseEntity<? extends CommonResponse> makeResponseEntity(HttpServletRequest request, CommonResponse comm) {

		HttpHeaders headers = new HttpHeaders();

		ResponseEntity<? extends CommonResponse> response = ResponseEntity.ok()
			.headers(headers)
			.body(comm);

		return response;
	}
}