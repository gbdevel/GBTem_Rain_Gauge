package com.gbsoft.rainfallcollector.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RagEquipmentResponse  extends CommonResponse<RagEquipmentValue> {
	
	private static final String MESSAGE = "단말기 생성 성공";
	
	public RagEquipmentResponse(RagEquipmentValue data) {
		 super("S000", MESSAGE, data);
	}

}
