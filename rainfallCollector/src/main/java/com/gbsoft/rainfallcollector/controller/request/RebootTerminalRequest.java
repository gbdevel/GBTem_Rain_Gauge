package com.gbsoft.rainfallcollector.controller.request;

import javax.validation.constraints.NotNull;

import com.gbsoft.rainfallcollector.exception.DateFormatInvalidException;
import com.gbsoft.rainfallcollector.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RebootTerminalRequest {

	@NotNull(message = "필수값 입니다.")
	private String terminalUuid;

	@NotNull(message = "필수값 입니다.")
	private String startDatetime;

	public void validate() {
		if (DateUtil.isValidDateTimeFormat(startDatetime)) {
			DateUtil.parseDateTime(startDatetime);
		} else {
			throw new DateFormatInvalidException();
		}

	}
}
