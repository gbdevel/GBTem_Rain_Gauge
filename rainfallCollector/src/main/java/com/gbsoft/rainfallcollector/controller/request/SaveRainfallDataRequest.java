package com.gbsoft.rainfallcollector.controller.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.gbsoft.rainfallcollector.exception.DateFormatInvalidException;
import com.gbsoft.rainfallcollector.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SaveRainfallDataRequest {

	@NotNull(message = "필수값 입니다.")
	private String terminalUuid;

	@NotNull(message = "필수값 입니다.")
	@Digits(integer = 3, fraction = 1)
	@PositiveOrZero
	private Double rainfallPerOneMinute;

	@NotNull(message = "필수값 입니다.")
	private String dateTime;

	public void validate() {

		if (DateUtil.isValidDateTimeFormat(dateTime)) {
			DateUtil.parseDateTime(dateTime);
		} else {
			throw new DateFormatInvalidException();
		}
	}
}
