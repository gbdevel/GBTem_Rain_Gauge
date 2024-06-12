package com.gbsoft.rainfallcollector.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbsoft.rainfallcollector.domain.RainFallLog;

public interface RainfallLogRepository extends JpaRepository<RainFallLog, Long> {
}
