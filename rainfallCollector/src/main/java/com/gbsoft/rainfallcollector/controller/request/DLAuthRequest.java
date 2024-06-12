package com.gbsoft.rainfallcollector.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DLAuthRequest {
	private String grant_type;
	private String client_id;
	private String client_secret;
	private String scope;
}
