package com.gbsoft.rainfallcollector.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gbsoft.rainfallcollector.controller.request.RainRecvDateRequest;
import com.gbsoft.rainfallcollector.domain.EquipInstLocation;
import com.gbsoft.rainfallcollector.domain.RainRecvDate;
import com.gbsoft.rainfallcollector.exception.OnSiteNotFoundException;
import com.gbsoft.rainfallcollector.repository.EquipmentInstLocRepository;
import com.gbsoft.rainfallcollector.repository.RainRecvDateRepository;
import com.gbsoft.rainfallcollector.util.DateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RainGaugeService {

	private final RainRecvDateRepository rainRecvDateRepository;

	private final EquipmentInstLocRepository equipmentInstLocRepository;
	
	/**
	 * 단말기에서 넘어온 강우량 데이터를 입력한다.
	 *
	 * @param saveRainfallDataRequest
	 */
	@Transactional
	public void rainRecvDate(RainRecvDateRequest rainRecvDateRequest) {

		LocalDate localDate = DateUtil.stringToLocalDate(rainRecvDateRequest.getRainGaugeSendDate());
		
		EquipInstLocation equipInstLocation = equipmentInstLocRepository
				.findByEquipUuidAndInstLocEndDtGreaterThanEqualAndUseYn(rainRecvDateRequest.getEquipUuid(), localDate, "Y")
				.orElseThrow(OnSiteNotFoundException::new);
			
		LocalDateTime localDateTime = DateUtil.stringToLocalDateTime(rainRecvDateRequest.getRainGaugeSendDate());

		rainRecvDateRepository.save(RainRecvDate.builder()
												.equipInstLocSeq(equipInstLocation)
												.siteInfoIdx(equipInstLocation.getSiteInfoIdx())
												.equipUuid(equipInstLocation.getEquipUuid())
												.rainGauge(rainRecvDateRequest.getRainGauge())
												.rainRecvDt(localDateTime)
												.build());
	}
}
