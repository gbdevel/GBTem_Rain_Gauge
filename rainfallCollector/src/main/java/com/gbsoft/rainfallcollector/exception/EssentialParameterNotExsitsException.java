package com.gbsoft.rainfallcollector.exception;

import java.util.Map;

public class EssentialParameterNotExsitsException extends ApiException {

	private static final String message = " 필수 파라미터가 누락되었습니다.";

	public EssentialParameterNotExsitsException() {
		super(message);
	}

	public EssentialParameterNotExsitsException(Map<String, String> fields) {
		super(message);
		fields.forEach(this::addValidation);
	}

	@Override
	public String getStatusCode() {
		return "E002";
	}

}
