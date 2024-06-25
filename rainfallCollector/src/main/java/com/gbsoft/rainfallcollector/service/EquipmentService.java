package com.gbsoft.rainfallcollector.service;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gbsoft.rainfallcollector.controller.request.InitEquipmentRequest;
import com.gbsoft.rainfallcollector.controller.request.RagEquipmentRequest;
import com.gbsoft.rainfallcollector.controller.request.RebootEquipmentRequest;
import com.gbsoft.rainfallcollector.domain.EquipByCust;
import com.gbsoft.rainfallcollector.domain.Equipment;
import com.gbsoft.rainfallcollector.exception.RegisteredEquipException;
import com.gbsoft.rainfallcollector.exception.TerminalNotFoundException;
import com.gbsoft.rainfallcollector.repository.EquipByCustRepository;
import com.gbsoft.rainfallcollector.repository.EquipmentRepository;
import com.gbsoft.rainfallcollector.util.DateUtil;
import com.gbsoft.rainfallcollector.util.RandomUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipmentService {
	
	private final EquipmentRepository equipmentRepository;
	
	private final EquipByCustRepository equipByCustRepository;
	
	@Transactional(readOnly = true)
	public Equipment initalizeEquipement(InitEquipmentRequest equipmentRequest) {
		
		Equipment equipment = equipmentRepository.findByMacAddress(equipmentRequest.getMacaddress())
				.orElseThrow(TerminalNotFoundException::new);
		
		return equipment;
	}

	@Transactional(readOnly = true)
	public EquipByCust rebootEquipment(RebootEquipmentRequest rebootEquipmentRequest) {

		// 존재하지 않는 단말기 확인.
		
		
		LocalDate localDate = DateUtil.stringToLocalDate(rebootEquipmentRequest.getStartDatetime());
		
		
		EquipByCust equipByCust = equipByCustRepository
				.findByEquipUuidAndMacaddressAndEcEndDtGreaterThanEqualAndUseYn(rebootEquipmentRequest.getEquipUuid(),rebootEquipmentRequest.getMacaddress(),localDate,"Y")
			.orElseThrow(TerminalNotFoundException::new);

		return equipByCust;
	}
	
	@Transactional
	public Equipment regEquipment(RagEquipmentRequest ragEquipmentRequest) {
		
		String equipUuid =  "RG" + RandomUtil.genneraterNum(10);
		
		equipmentRepository.findByMacAddress(ragEquipmentRequest.getMacAddress())
				.ifPresentOrElse(macAddress -> {
					System.out.println("이미 사용중인 macAddress : " + macAddress.getMacAddress());
					throw new RegisteredEquipException("이미 사용중인 macAddress 입니다.");
				},
				() -> {
					equipmentRepository.findByEquipUuid(equipUuid)
							.ifPresentOrElse(uuid -> {    
										 regEquipment(ragEquipmentRequest);
							},
							() -> equipmentRepository.save(Equipment.builder()
										.equipUuid(equipUuid)
										.macAddress(ragEquipmentRequest.getMacAddress())
										.equipType(ragEquipmentRequest.getEquipType())
										.equipName(ragEquipmentRequest.getEquipName())
										.useYn("Y")
										.build())
							);
						});	
		
		Equipment equipment = equipmentRepository.findByEquipUuid(equipUuid)
				.orElseThrow(() ->
						new RegisteredEquipException("등록되지 않은 단말기 입니다.")
						);
		
		return equipment;
	}
}
