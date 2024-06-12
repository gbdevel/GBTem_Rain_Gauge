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
public class RainRecvDateRequest {

	@NotNull(message = "필수값 입니다.")
	private String equipUuid;

	@NotNull(message = "필수값 입니다.")
	@Digits(integer = 3, fraction = 1)
	@PositiveOrZero
	private Double rainGauge;

	@NotNull(message = "필수값 입니다.")
	private String rainGaugeSendDate;

	public void validate() {

		if (DateUtil.isValidDateTimeFormat(rainGaugeSendDate)) {
			DateUtil.parseDateTime(rainGaugeSendDate);
		} else {
			throw new DateFormatInvalidException();
		}
	}
}
