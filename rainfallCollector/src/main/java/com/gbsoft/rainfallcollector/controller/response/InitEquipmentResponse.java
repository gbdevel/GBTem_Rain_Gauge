package com.gbsoft.rainfallcollector.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InitEquipmentResponse extends CommonResponse<InitEquipmentValue> {
	
	private static final String MESSAGE = "단말기 인증 성공";
	
	public InitEquipmentResponse(InitEquipmentValue data) {
        super("S000", MESSAGE, data);
    }

}
