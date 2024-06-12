package com.gbsoft.rainfallcollector.controller.response;

import com.gbsoft.rainfallcollector.domain.Terminal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InitTerminalValue {

	private String terminalUuid;

	public InitTerminalValue(Terminal terminal) {
		this.terminalUuid = terminal.getTerminalUuid();
	}
}
