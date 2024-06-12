package com.gbsoft.rainfallcollector.exception;

public class TerminalNotFoundException extends ApiException {

	private static final String message = "등록되지 않은 단말기 입니다.";

	public TerminalNotFoundException() {
		super(message);
	}

	@Override
	public String getStatusCode() {
		return "E100";
	}

}
