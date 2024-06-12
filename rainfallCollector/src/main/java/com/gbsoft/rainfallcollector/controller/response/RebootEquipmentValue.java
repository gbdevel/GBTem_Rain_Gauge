package com.gbsoft.rainfallcollector.controller.response;

import com.gbsoft.rainfallcollector.domain.EquipByCust;
import com.gbsoft.rainfallcollector.domain.Equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RebootEquipmentValue {

	private String equipUuid;

	public RebootEquipmentValue(EquipByCust equipment) {    
		this.equipUuid = equipment.getEquipUuid().toString();
	}
}
