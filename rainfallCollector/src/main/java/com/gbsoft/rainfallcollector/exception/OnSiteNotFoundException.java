package com.gbsoft.rainfallcollector.exception;

public class OnSiteNotFoundException extends ApiException {

	private static final String message = "연결된 현장이 없는 단말기 입니다.";
	
	public OnSiteNotFoundException() {
		super(message);
	}
	
	@Override
	public String getStatusCode() {

		return "E300";
	}
}
