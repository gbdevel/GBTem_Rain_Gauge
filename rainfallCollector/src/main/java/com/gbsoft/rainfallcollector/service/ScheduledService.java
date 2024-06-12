package com.gbsoft.rainfallcollector.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gbsoft.rainfallcollector.domain.EquipByCust;
import com.gbsoft.rainfallcollector.domain.EquipInstLocation;
import com.gbsoft.rainfallcollector.domain.Terminal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledService {

	private static final Integer CYCLE = 5;

	private final RainfallService rainfallService;

	private final TerminalService terminalService;
	
	private final RainGaugeSendService rainGaugeSendService;
	

//	@Scheduled(cron = "0 */5 * * * *") // 정각을 기준으로 5분 마다 실행함./
	public void scheduleTaskUsingCronExpression() {
		//long now = System.currentTimeMillis() / 1000;
		//log.info("schedule tasks using cron jobs - {}", now);

		String accessToken = rainfallService.authDL();

		Map<Terminal, Double> allTerminalRainfall = rainfallService.findAllTerminalRainfall(CYCLE);

		allTerminalRainfall.forEach((terminal, rainfall) -> {
			rainfallService.transmit(accessToken, terminal, rainfall);
		});

	}
	
	@Scheduled(cron = "0 */5 * * * *") // 정각을 기준으로 5분 마다 실행함.
	public void rainGaugeSendDate() {

		String accessToken = rainGaugeSendService.authDL();

		Map<EquipInstLocation, Double> allTerminalRainfall = rainGaugeSendService.findAllEquipmentRainRecvDate();

		allTerminalRainfall.forEach((equipment, rainGauge) -> {
			
			rainGaugeSendService.transmit(accessToken, equipment, rainGauge);
		});

	}
}
