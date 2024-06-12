package com.gbsoft.rainfallcollector.service;

import static com.gbsoft.rainfallcollector.service.DLInterface.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.gbsoft.rainfallcollector.controller.request.SaveRainfallDataRequest;
import com.gbsoft.rainfallcollector.domain.RainFall;
import com.gbsoft.rainfallcollector.domain.RainFallLog;
import com.gbsoft.rainfallcollector.domain.Terminal;
import com.gbsoft.rainfallcollector.exception.TerminalNotFoundException;
import com.gbsoft.rainfallcollector.repository.RainfallLogRepository;
import com.gbsoft.rainfallcollector.repository.RainfallRepository;
import com.gbsoft.rainfallcollector.repository.TerminalRepository;
import com.gbsoft.rainfallcollector.service.dto.AuthResponseBodyDto;
import com.gbsoft.rainfallcollector.service.dto.ResponseBodyDto;
import com.gbsoft.rainfallcollector.util.DateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RainfallService {
	private final TerminalRepository terminalRepository;

	private final RainfallRepository rainfallRepository;

	private final RainfallLogRepository rainfallLogRepository;

	/**
	 * 단말기에서 넘어온 강우량 데이터를 입력한다.
	 *
	 * @param saveRainfallDataRequest
	 */
	public void saveRainFall(SaveRainfallDataRequest saveRainfallDataRequest) {

		Terminal terminal = terminalRepository.findByTerminalUuid(saveRainfallDataRequest.getTerminalUuid())
			.orElseThrow(TerminalNotFoundException::new);

		LocalDateTime localDateTime = DateUtil.stringToLocalDateTime(saveRainfallDataRequest.getDateTime());

		rainfallRepository.save(RainFall.builder()
				.terminal(terminal)
				.rainfallPerOneMinute(saveRainfallDataRequest.getRainfallPerOneMinute())
				.dateTime(localDateTime)
				.build());
	}

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
	public DLTransmitRequest transmit(String token, Terminal terminal, Double rainFall) {

		try {

			RestTemplate restTemplate = generateRestTemplate();
			String uri = makeUri(transmitUrl);

			String stringFormatNowDatetime = DateUtil.localDateTimeToString(LocalDateTime.now());
			Long logingId = transmitLog(terminal.getTerminalUuid(), stringFormatNowDatetime, "060501", rainFall);

			DLTransmitRequest build = DLTransmitRequest.builder()
				.P_CD_RAF_EQPT(terminal.getTerminalUuid())
				.P_DNT_MSMT(stringFormatNowDatetime)
				.P_NO_SEQ(String.valueOf(logingId))
				.P_QT_RAF(String.valueOf(rainFall))
				.P_CD_SITE("060501")
				.build();

			//log.info("build : {}", build);
			Map<String, String> map = new HashMap<>();
			map.put("P_CD_RAF_EQPT", terminal.getTerminalUuid());
			map.put("P_DNT_MSMT", stringFormatNowDatetime);
			map.put("P_NO_SEQ", String.valueOf(logingId));
			map.put("P_QT_RAF", String.valueOf(rainFall));
			map.put("P_CD_SITE", "060501");
			HttpEntity request = makeTransmitHttpEntity(map, token);

			ResponseEntity<ResponseBodyDto> response = restTemplate.postForEntity(uri, request, ResponseBodyDto.class);

			checkTransmitLogSuccess(logingId);

			makeResultMap(response);


		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().value() == HttpStatus.SC_UNAUTHORIZED) {
				String accessToken = authDL();
				transmit(accessToken, terminal, rainFall); // 재전송.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<Terminal, Double> findAllTerminalRainfall(Integer cycle) {

		HashMap<Terminal, Double> terminalDoubleHashMap = new HashMap<>();

		List<Terminal> terminals = terminalRepository.findAllByMacaddressNotNull();

		for (int i = 0; i < terminals.size(); i++) {
			Terminal terminal = terminals.get(i);
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime target = now.minusMinutes(cycle);

			List<RainFall> list = rainfallRepository.findByTerminalAndDateTimeBetween(terminal, target, now);

			/* 2024-02-08 안재욱 매니저 요청. 데이터 없어도 일단 0 전송하도록 처리.
			트렌젝션 처리 안되어있어서 powerOff 안되고 있으.ㅁ
			if (list.size() < 5) {
				terminal.powerOff();
				continue;
			}
			*/

			AtomicReference<Double> sum = new AtomicReference<>((double)0);
			list.forEach(r -> {
				sum.updateAndGet(v -> v + r.getRainfallPerOneMinute());
			});

			terminalDoubleHashMap.put(terminal, sum.get());
		}

		return terminalDoubleHashMap;
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

	private HttpEntity makeHttpEntity(Object request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);


		return new HttpEntity<>(request, headers);
	}

	private HttpEntity makeTransmitHttpEntity(Map<String, String> build, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);

		return new HttpEntity<>(build, headers);
	}

	private String makeUri(String url) {
		return UriComponentsBuilder.fromHttpUrl(url)
			.build(false)
			.toString();
	}

	private HashMap<String, Object> makeResultMap(ResponseEntity response) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("statusCode", response.getStatusCodeValue());
		resultMap.put("header", response.getHeaders());
		resultMap.put("body", response.getBody());

		return resultMap;
	}

	private Long transmitLog(String terminalUuid, String formattedDateTime, String number,
		Double rainFall) {

		LocalDateTime localDateTime = DateUtil.stringToLocalDateTime(formattedDateTime);

		RainFallLog save = rainfallLogRepository.save(RainFallLog.builder()
			.terminalUuid(terminalUuid)
			.stieCd(number)
			.rainfall(rainFall)
			.transmitDate(localDateTime)
			.build());

		return save.getId();
	}

	private void checkTransmitLogSuccess(Long id) {
		RainFallLog rainFallLog = rainfallLogRepository.findById(id)
			.orElseThrow(RuntimeException::new);

		rainFallLog.transmitSuccessCheck();
	}
}