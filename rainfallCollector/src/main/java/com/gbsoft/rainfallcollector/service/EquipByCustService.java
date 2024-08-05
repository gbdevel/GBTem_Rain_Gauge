package com.gbsoft.rainfallcollector.service;

import com.gbsoft.rainfallcollector.domain.Equipment;
import com.gbsoft.rainfallcollector.repository.EquipByCustRepository;
import com.gbsoft.rainfallcollector.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipByCustService {
    private final EquipmentRepository equipmentRepository;
    private final EquipByCustRepository equipByCustRepository;

    @Transactional
    public void saveEquipByCust(String macAddress) {
        Equipment equipment = equipmentRepository.findByMacAddress(macAddress).orElseThrow(IllegalArgumentException::new);

        if(equipByCustRepository.findByMacaddress(macAddress).isPresent()) {
            throw new IllegalArgumentException("macAddress가 존재 합니다");
        }

        equipByCustRepository.save(equipment.from());
    }
}
