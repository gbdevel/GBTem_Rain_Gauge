package com.gbsoft.rainfallcollector.controller.request;

import javax.validation.constraints.NotEmpty;
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

	@NotEmpty(message = "macAddress는 필수값 입니다.")
	private String macAddress;

	@NotEmpty(message = "equipType은 필수값 입니다.")
	private String equipType;
	
	private String equipName;
	
}
