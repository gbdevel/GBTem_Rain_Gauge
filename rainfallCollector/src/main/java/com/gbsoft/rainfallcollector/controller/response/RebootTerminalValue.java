package com.gbsoft.rainfallcollector.controller.response;

import com.gbsoft.rainfallcollector.domain.Terminal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RebootTerminalValue {

	private String terminalUuid;

	public RebootTerminalValue(Terminal terminal) {
		this.terminalUuid = terminal.getTerminalUuid();
	}
}
