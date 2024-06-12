package com.gbsoft.rainfallcollector.domain;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "rain_recv_date")
@Entity
@SequenceGenerator(name = "RAIN_RECV_DATE_GEN", sequenceName = "SEQ_RAIN_RECV_DATE", initialValue = 1, allocationSize = 1)
public class RainRecvDate extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RAIN_RECV_DATE_GEN")
	@Column(name = "RAIN_RECV_DATE_SEQ")
	private Long rainRecvDateSeq;
	
	@ManyToOne
	@JoinColumn(name = "EQUIP_INST_LOC_SEQ")
	private EquipInstLocation equipInstLocSeq;
	
	@ManyToOne
	@JoinColumn(name = "SITE_INFO_IDX")
	private Onsiteinfo siteInfoIdx;
	
	@Column(name = "EQUIP_UUID")
	private String equipUuid;
	
	@Column(name = "RAINGAUGE")
	private Double rainGauge;
	
	@Column(name = "RAIN_RECV_DT")
	private LocalDateTime rainRecvDt;
	
	
}
