package com.gbsoft.rainfallcollector.controller.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class DLTransmitRequest implements Serializable {
	private String P_CD_SITE;
	private String P_CD_RAF_EQPT;
	private String P_DNT_MSMT;
	private String P_NO_SEQ;
	private String P_QT_RAF;

}
