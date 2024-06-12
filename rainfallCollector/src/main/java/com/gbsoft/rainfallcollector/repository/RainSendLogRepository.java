package com.gbsoft.rainfallcollector.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbsoft.rainfallcollector.domain.RainSendLog;

public interface RainSendLogRepository extends JpaRepository<RainSendLog, Long>{

}
