package com.gbsoft.rainfallcollector.exception;

import java.util.HashMap;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
	public final HashMap<String, Object> validation = new HashMap<>();

	public ApiException(String message) {
		super(message);
	}

	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public abstract String getStatusCode();

	public void addValidation(String fieldName, String message) {
		validation.put(fieldName, message);
	}

}