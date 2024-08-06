package com.gbsoft.rainfallcollector.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RagEquipmentResponse  extends CommonResponse<String> {
	
	private static final String MESSAGE = "단말기 생성 성공";
	
	public RagEquipmentResponse(String data) {
		 super("S000", MESSAGE, data);
	}

}
