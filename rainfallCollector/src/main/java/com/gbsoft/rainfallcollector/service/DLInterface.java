package com.gbsoft.rainfallcollector.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DLInterface {

	public static String authUrl;
	public static String grantType = "client_credentials";
	public static String clientId;
	public static String clientSecret;
	public static String scope = "dgw2030.api";
	//public static final String TRANSMIT_URL = "https://apitest.dlenc.co.kr/dgw20-30/400067";
	public static String transmitUrl;

	@Value("${dlInterface.auth_url}")
	private void setAuthUrl(String value) {
		DLInterface.authUrl = value;
	}

	@Value("${dlInterface.client_id}")
	private void setClientId(String value) {
		DLInterface.clientId = value;
	}

	@Value("${dlInterface.client_secret}")
	private void setClientSecret(String value) {
		DLInterface.clientSecret = value;
	}

	@Value("${dlInterface.transmit_url}")
	private void setTransmitUrl(String value) {
		DLInterface.transmitUrl = value;
	}
}
