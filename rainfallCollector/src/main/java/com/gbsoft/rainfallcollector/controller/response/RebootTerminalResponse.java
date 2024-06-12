package com.gbsoft.rainfallcollector.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RebootTerminalResponse extends CommonResponse<RebootTerminalValue> {

    private static final String MESSAGE = "단말기 재부팅 완료";

    public RebootTerminalResponse(RebootTerminalValue data) {
        super("",MESSAGE, data);
    }
}
