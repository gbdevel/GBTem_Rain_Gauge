package com.gbsoft.rainfallcollector.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RainRecvDateResoponse extends CommonResponse<RainRecvDateValue> {

	private static final String MESSAGE = "분당 강우량 데이터 입력 성공";
	
	public RainRecvDateResoponse(RainRecvDateValue data) {
		super("S000", MESSAGE, data);
	}

}
