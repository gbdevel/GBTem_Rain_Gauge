package com.gbsoft.rainfallcollector.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbsoft.rainfallcollector.domain.EquipInstLocation;


public interface EquipmentInstLocRepository extends JpaRepository<EquipInstLocation, Long> {
	
	Optional<EquipInstLocation> findTop1ByEquipUuidOrderByCrtDtDesc(String equipUuid);
	
	Optional<EquipInstLocation> findByEquipUuidAndInstLocEndDtGreaterThanEqualAndUseYn(String equipUuid, LocalDate localDt, String UseYn);
	
}
