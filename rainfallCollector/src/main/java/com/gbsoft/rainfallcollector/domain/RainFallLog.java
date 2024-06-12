package com.gbsoft.rainfallcollector.domain;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "rainfall_logSend")
public class RainFallLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "terminal_uuid")
	private String terminalUuid;

	@Column(name = "transmit_date")
	private LocalDateTime transmitDate;

	@Column(name = "site_cd")
	private String stieCd;

	private Double rainfall;

	@Column(name = "success_yn")
	private boolean successYn;

	public void transmitSuccessCheck() {
		this.successYn = true;
		this.transmitDate = LocalDateTime.now();
	}
}
