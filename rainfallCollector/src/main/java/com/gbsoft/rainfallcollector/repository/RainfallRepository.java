package com.gbsoft.rainfallcollector.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbsoft.rainfallcollector.domain.RainFall;
import com.gbsoft.rainfallcollector.domain.Terminal;

public interface RainfallRepository extends JpaRepository<RainFall, Long> {
	List<RainFall> findByTerminalAndDateTimeBetween(Terminal terminal, LocalDateTime start, LocalDateTime end);
}
