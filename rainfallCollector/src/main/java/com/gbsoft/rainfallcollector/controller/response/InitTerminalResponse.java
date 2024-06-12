package com.gbsoft.rainfallcollector.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InitTerminalResponse extends CommonResponse<InitTerminalValue> {

    private static final String MESSAGE = "단말기 초기화 완료";

    public InitTerminalResponse(InitTerminalValue data) {
        super("", MESSAGE, data);
    }
}
