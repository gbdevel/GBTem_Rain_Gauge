package com.gbsoft.rainfallcollector.controller.response;


import com.gbsoft.rainfallcollector.domain.Equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InitEquipmentValue {
	
	private String equipUuid;

	public InitEquipmentValue(Equipment equipment) {
		this.equipUuid = equipment.getEquipUuid();
	}
}
