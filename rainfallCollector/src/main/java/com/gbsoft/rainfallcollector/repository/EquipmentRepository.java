package com.gbsoft.rainfallcollector.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gbsoft.rainfallcollector.domain.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {
	
	Optional<Equipment> findByEquipUuid(String equipUuid);
	
	Optional<Equipment> findByMacAddress(String macAddress);

	boolean existsByMacAddress(String macAddress);
	
	boolean existsByEquipUuid(String equipUuid);
}
 