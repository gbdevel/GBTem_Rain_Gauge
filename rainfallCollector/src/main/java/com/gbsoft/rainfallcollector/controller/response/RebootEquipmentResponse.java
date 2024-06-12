package com.gbsoft.rainfallcollector.controller.response;

public class RebootEquipmentResponse extends CommonResponse<RebootEquipmentValue>{

	private static final String MESSAGE = "단말기 재부팅 완료";
	
	public RebootEquipmentResponse(RebootEquipmentValue data) {
		super("S000", MESSAGE, data);
	}

}
