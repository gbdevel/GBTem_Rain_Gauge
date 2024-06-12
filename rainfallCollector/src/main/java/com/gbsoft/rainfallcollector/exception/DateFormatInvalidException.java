package com.gbsoft.rainfallcollector.exception;

public class DateFormatInvalidException extends ApiException {

	private static final String message = " 올바른 형식의 파라미터가 아닙니다.";

	public DateFormatInvalidException() {
		super(message);
	}
	
	public DateFormatInvalidException(String arg) {
		super(arg + message);
	}

	@Override
	public String getStatusCode() {
		return "E003";
	}

}
