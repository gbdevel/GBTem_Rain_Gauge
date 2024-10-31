package com.gbsoft.rainfallcollector.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.gbsoft.rainfallcollector.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gbsoft.rainfallcollector.domain.EquipByCust;



public interface EquipByCustRepository extends JpaRepository<EquipByCust, Long>{
	
	Optional<EquipByCust> findByEquipUuidAndMacaddressAndEcEndDtGreaterThanEqualAndUseYn(String equipUuid, String macaddress, LocalDate localDateTime, String useYn);
	
	List<EquipByCust> findByCustSeqAndEcEndDtGreaterThanEqualAndUseYn( String custSeq, LocalDate today,  String UseYn);

	Optional<EquipByCust> findByMacaddress(String macAddress);
}
