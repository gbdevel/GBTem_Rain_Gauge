package com.gbsoft.rainfallcollector.exception;

public class RegisteredEquipException extends ApiException {

	//private static final String message = "이미 사용중인 macAddress 입니다.";

	public RegisteredEquipException() {
		super("장비정보 등록 오류");
	}

	public RegisteredEquipException(String message) {
		super(message);
	}
	
	@Override
	public String getStatusCode() {
		return "E101";
	}
}
