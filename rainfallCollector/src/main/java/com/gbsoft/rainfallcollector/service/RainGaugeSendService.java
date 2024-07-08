package com.gbsoft.rainfallcollector.service;

import static com.gbsoft.rainfallcollector.service.DLInterface.authUrl;
import static com.gbsoft.rainfallcollector.service.DLInterface.clientId;
import static com.gbsoft.rainfallcollector.service.DLInterface.clientSecret;
import static com.gbsoft.rainfallcollector.service.DLInterface.grantType;
import static com.gbsoft.rainfallcollector.service.DLInterface.scope;
import static com.gbsoft.rainfallcollector.service.DLInterface.transmitUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.http.HttpStatus;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.gbsoft.rainfallcollector.controller.request.DLAuthRequest;
import com.gbsoft.rainfallcollector.controller.request.DLTransmitRequest;
import com.gbsoft.rainfallcollector.domain.EquipByCust;
import com.gbsoft.rainfallcollector.domain.EquipInstLocation;
import com.gbsoft.rainfallcollector.domain.RainRecvDate;
import com.gbsoft.rainfallcollector.domain.RainSendLog;
import com.gbsoft.rainfallcollector.domain.Terminal;
import com.gbsoft.rainfallcollector.exception.OnSiteNotFoundException;
import com.gbsoft.rainfallcollector.repository.EquipByCustRepository;
import com.gbsoft.rainfallcollector.repository.EquipmentInstLocRepository;
import com.gbsoft.rainfallcollector.repository.RainRecvDateRepository;
import com.gbsoft.rainfallcollector.repository.RainSendLogRepository;
import com.gbsoft.rainfallcollector.service.dto.AuthResponseBodyDto;
import com.gbsoft.rainfallcollector.service.dto.ResponseBodyDto;
import com.gbsoft.rainfallcollector.util.DateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RainGaugeSendService {

	private final EquipByCustRepository equipByCustRepository;
	
	private final RainRecvDateRepository rainRecvDateRepository;
	
	private final RainSendLogRepository rainSendLogRepository;
	
	private final EquipmentInstLocRepository equipmentInstLocRepository;
	
	public String authDL() {

		log.info("auth info ====");
		log.info("URL : {}",  authUrl);
		log.info("client_id : {}",  clientId);
		log.info("client_secret : {}",  clientSecret);

		RestTemplate restTemplate = generateRestTemplate();
		String uri = makeUri(authUrl);

		DLAuthRequest dlAuthRequest = DLAuthRequest.builder()
													.grant_type(grantType)
													.client_id(clientId)
													.client_secret(clientSecret)
													.scope(scope)
													.build();
		HttpEntity httpEntity = makeHttpEntity(dlAuthRequest);

		try {
			ResponseEntity<AuthResponseBodyDto> response = restTemplate.postForEntity(uri, httpEntity, AuthResponseBodyDto.class);
			HashMap<String, Object> resultMap = makeResultMap(response);

			return response.getBody().getToken().getAccess_token();

		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 5분 주기로 강우량 데이터를 DL로 전송한다.
	 */
	@Transactional
	public DLTransmitRequest transmit(String token, EquipInstLocation equipment, Double rainGauge) {

		try {

			RestTemplate restTemplate = generateRestTemplate();
			String uri = makeUri(transmitUrl);

			String stringFormatNowDatetime = DateUtil.localDateTimeToString(LocalDateTime.now());
			Long logingId = transmitLog(equipment.getEquipUuid(), stringFormatNowDatetime, equipment.getOnSiteCode(), rainGauge);

//			log.info("build : {}", build);
			Map<String, String> map = new HashMap<>();
			map.put("P_CD_RAF_EQPT", equipment.getEquipUuid());
			map.put("P_DNT_MSMT", stringFormatNowDatetime);
			map.put("P_NO_SEQ", String.valueOf(logingId));
			map.put("P_QT_RAF", String.valueOf(rainGauge));
			map.put("P_CD_SITE", equipment.getOnSiteCode());
			HttpEntity request = makeTransmitHttpEntity(map, token);

			ResponseEntity<ResponseBodyDto> response = restTemplate.postForEntity(uri, request, ResponseBodyDto.class);

			checkTransmitLogSuccess(logingId);

			makeResultMap(response);


		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().value() == HttpStatus.SC_UNAUTHORIZED) {
				String accessToken = authDL();
				transmit(accessToken, equipment, rainGauge); // 재전송.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HashMap<EquipInstLocation, Double> findAllEquipmentRainRecvDate() {

		HashMap<EquipInstLocation, Double> equipDoubleHashMap = new HashMap<>();
		
		LocalDate today = LocalDate.now();
		
		List<EquipByCust> equipements = equipByCustRepository.findByCustSeqAndEcEndDtGreaterThanEqualAndUseYn("1", today, "Y");

		
		for (int i = 0; i < equipements.size(); i++) {
			EquipByCust equipement = equipements.get(i);
			LocalDateTime now = LocalDateTime.now().minusMinutes(2);
			LocalDateTime target = now.minusMinutes(7);
			
			Optional<EquipInstLocation> equipInstLocation = equipmentInstLocRepository.findByEquipUuidAndInstLocEndDtGreaterThanEqualAndUseYn(equipement.getEquipUuid(), today, "Y");
			
			if(!equipInstLocation.isPresent()) {
				continue;
			}

			EquipInstLocation equipInstLoc = equipInstLocation.get();
			
			List<RainRecvDate> list = rainRecvDateRepository.findByEquipUuidAndSiteInfoIdxAndRainRecvDtBetween(equipInstLoc.getEquipUuid(), equipInstLoc.getSiteInfoIdx() ,target, now);

			/* 2024-02-08 안재욱 매니저 요청. 데이터 없어도 일단 0 전송하도록 처리.
			트렌젝션 처리 안되어있어서 powerOff 안되고 있으.ㅁ
			if (list.size() < 5) {
				terminal.powerOff();
				continue;
			}
			*/

			AtomicReference<Double> sum = new AtomicReference<>((double)0);
			list.forEach(r -> {
				sum.updateAndGet(v -> v + r.getRainGauge());
			});

			equipDoubleHashMap.put(equipInstLoc, sum.get());
		}

		return equipDoubleHashMap;
	}
	
	
	private RestTemplate generateRestTemplate() {

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(5000);
		factory.setReadTimeout(5000);

		CloseableHttpClient httpClient = HttpClientBuilder.create()
			.setMaxConnTotal(50)
			.setMaxConnPerRoute(20)
			.build();

		factory.setHttpClient(httpClient);

		return new RestTemplate(factory);
	}
	
	private String makeUri(String url) {
		return UriComponentsBuilder.fromHttpUrl(url)
			.build(false)
			.toString();
	}
	
	private HttpEntity makeHttpEntity(Object request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);


		return new HttpEntity<>(request, headers);
	}

	private HashMap<String, Object> makeResultMap(ResponseEntity response) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("statusCode", response.getStatusCodeValue());
		resultMap.put("header", response.getHeaders());
		resultMap.put("body", response.getBody());

		return resultMap;
	}
	
	private Long transmitLog(String equipUuid, String formattedDateTime, String number,
		Double rainGauge) {

		LocalDateTime localDateTime = DateUtil.stringToLocalDateTime(formattedDateTime);

		RainSendLog sendDl = rainSendLogRepository.save(
				RainSendLog.builder()
				.equipUuid(equipUuid)
				.rainSendDt(localDateTime)
				.onSiteCode(number)
				.raingaugeSendDate(rainGauge)
				.build()
				 );
				
		return sendDl.getRainSendLogdateSeq();
	}
	
	private HttpEntity makeTransmitHttpEntity(Map<String, String> build, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);

		return new HttpEntity<>(build, headers);
	}
	
	private void checkTransmitLogSuccess(Long id) {
		RainSendLog rainGaugeSendLog = rainSendLogRepository.findById(id)
			.orElseThrow(RuntimeException::new);

		rainGaugeSendLog.transmitSuccessCheck();
	}

}
