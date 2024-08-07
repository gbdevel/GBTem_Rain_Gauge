package com.gbsoft.rainfallcollector.domain;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "rain_send_logdate")
@Entity
@SequenceGenerator(name = "RAIN_SEND_LOGDATE_GEN", sequenceName = "SEQ_RAIN_SEND_LOGDATE", initialValue = 1, allocationSize = 1)
public class RainSendLog extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RAIN_SEND_LOGDATE_GEN")
	@Column(name = "RAIN_SEND_LOGDATE_SEQ")
	private Long rainSendLogdateSeq;
	
	@Column(name = "EQUIP_UUID")
	private String equipUuid;
	
	@Column(name = "RAIN_SEND_DT")
	private LocalDateTime rainSendDt;
	
	@Column(name = "ONSITE_CODE")
	private String onSiteCode;
	
	@Column(name = "RAINGAUGE_SEND_DATE")
	private Double raingaugeSendDate;
	
	@Column(name = "SUCCESS_YN")
	private boolean successYn;

	@Column(name = "MODIFY_YN")
	private boolean modifyYn;

	public void transmitSuccessCheck(EquipInstLocation equipment) {
		this.successYn = true;
		this.rainSendDt = LocalDateTime.now();
		if (equipment.isModify()) {
			this.modifyYn = true;
		}
	}
}
