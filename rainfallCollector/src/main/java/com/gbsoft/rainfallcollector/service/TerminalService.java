package com.gbsoft.rainfallcollector.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gbsoft.rainfallcollector.controller.request.InitTerminalRequest;
import com.gbsoft.rainfallcollector.controller.request.RebootTerminalRequest;
import com.gbsoft.rainfallcollector.domain.Terminal;
import com.gbsoft.rainfallcollector.exception.TerminalNotFoundException;
import com.gbsoft.rainfallcollector.repository.TerminalRepository;
import com.gbsoft.rainfallcollector.util.DateUtil;
import com.gbsoft.rainfallcollector.util.UUIDGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TerminalService {

	private final TerminalRepository terminalRepository;


	public Terminal initializeTerminal(InitTerminalRequest terminalRequest) {

		// 기존에 등록되 있는 맥 어드레스가 있다면?

		LocalDateTime localDateTime = DateUtil.stringToLocalDateTime(terminalRequest.getStartDatetime());
		UUID uuid = UUIDGenerator.generateUUID();

		Terminal savedTerminal = terminalRepository.save(Terminal.builder()
			.terminalUuid(uuid.toString())
			.macaddress(terminalRequest.getMacaddress())
			.startDateTime(localDateTime)
			.isPowered(true)
			.build());

		return savedTerminal;
	}

	@Transactional
	public Terminal rebootTerminal(RebootTerminalRequest terminalRequest) {

		// 존재하지 않는 단말기 확인.

		Terminal terminal = terminalRepository.findByTerminalUuid(terminalRequest.getTerminalUuid())
			.orElseThrow(TerminalNotFoundException::new);

		LocalDateTime localDateTime = DateUtil.stringToLocalDateTime(terminalRequest.getStartDatetime());
		terminal.reboot(localDateTime);

		return terminal;
	}
}  
