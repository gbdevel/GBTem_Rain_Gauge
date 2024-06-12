package com.gbsoft.rainfallcollector.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbsoft.rainfallcollector.domain.Onsiteinfo;
import com.gbsoft.rainfallcollector.domain.RainRecvDate;

public interface RainRecvDateRepository extends JpaRepository<RainRecvDate, Long> {
	
	List<RainRecvDate> findByEquipUuidAndSiteInfoIdxAndRainRecvDtBetween(String uuid, Onsiteinfo onsiteinfo, LocalDateTime start, LocalDateTime end);
}
