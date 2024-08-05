package com.gbsoft.rainfallcollector.controller;

import com.gbsoft.rainfallcollector.controller.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gbsoft.rainfallcollector.controller.response.ErrorResponse;
import com.gbsoft.rainfallcollector.exception.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleException(Exception ex) {
		return new ErrorResponse("E500", "서버에러", "관리자에게 문의해주세요");
	}

	@ExceptionHandler(ApiException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleApiException(ApiException ex) {
		return new ErrorResponse(ex.getStatusCode(), ex.getMessage(), ex.getValidation());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorResponse illegalArgumentException(IllegalArgumentException ex) {
		return new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage(), null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException ex) {
		return new ErrorResponse("E002", "필수 파라미터가 누락되었습니다", ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
	}
}
