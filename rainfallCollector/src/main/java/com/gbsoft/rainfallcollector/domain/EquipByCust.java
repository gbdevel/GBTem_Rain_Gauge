package com.gbsoft.rainfallcollector.domain;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "equip_by_cust")
@Entity
@SequenceGenerator(name = "EQUIP_BY_CUST_GEN", sequenceName = "SEQ_EQUIP_BY_CUST", allocationSize = 1)
public class EquipByCust extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EQUIP_BY_CUST_GEN")
	@Column(name = "EQUIP_BY_CUST_SEQ")
	private Long equipByCustSeq;
	
	@Column(name = "EQUIP_UUID")
	private String equipUuid;
	
	@Column(name = "CUST_SEQ")
	private String custSeq;
	
	@Column(name = "EC_NAME")
	private String ecName;
	
	@Column(name = "EC_START_DT")
	private LocalDate ecStartDt;
	
	@Column(name = "EC_END_DT")
	private LocalDate ecEndDt;
	
	@Column(name = "USE_YN")
	private String useYn;
	
	@Column(name = "REMARK_1")
	private String remake1;
	
	@Column(name = "MACADDRESS")
	private String macaddress;
	
	@OneToOne
	@JoinColumn(name = "EQUIP_BY_CUST_SEQ")
	private EquipInstLocation EquipInstLocation;

}
