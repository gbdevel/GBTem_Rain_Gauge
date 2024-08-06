package com.gbsoft.rainfallcollector.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class AddEquipByCustRequest {
    @NotEmpty(message = "macAddress는 필수 값 입니다")
    private String macAddress;
}
