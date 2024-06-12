package com.gbsoft.rainfallcollector.controller.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RagEquipmentRequest {

	@NotNull(message = "필수값 입니다.")
	private String macAddress;

	@NotNull(message = "필수값 입니다.")
	private String equipType;
	
}
