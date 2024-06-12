package com.gbsoft.rainfallcollector.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbsoft.rainfallcollector.domain.Terminal;

public interface TerminalRepository extends JpaRepository<Terminal, Long> {
	
	Optional<Terminal> findByTerminalUuid(String terminalUuid);

	List<Terminal> findAllByMacaddressNotNull();
}
