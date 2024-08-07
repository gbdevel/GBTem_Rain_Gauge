package com.gbsoft.rainfallcollector.domain;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "equip_inst_loc")
@Entity
@SequenceGenerator(name = "EQUIP_INST_LOC_GEN", sequenceName = "SEQ_EQUIP_INST_LOC",  allocationSize = 1)
public class EquipInstLocation extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EQUIP_INST_LOC_GEN")
	@Column(name = "EQUIP_INST_LOC_SEQ")
	private Long equipInstLocSeq;
	
	@ManyToOne
	@JoinColumn(name = "SITE_INFO_IDX")
	private Onsiteinfo siteInfoIdx;
	
	@Column(name = "EQUIP_UUID")
	private String equipUuid;
	
	@Column(name = "EQUIP_BY_CUST_SEQ")
	private Long equipByCustSeq;
	
	@Column(name = "ONSITE_CODE")
	private String onSiteCode;
	
	@Column(name = "INST_LOC_1")
	private String instLoc1;
	
	@Column(name = "INST_LOC_2")
	private String instLoc2;
	
	@Column(name = "INST_LOC_START_DT")
	private LocalDate instLocStartDt;
	
	@Column(name = "INST_LOC_END_DT")
	private LocalDate instLocEndDt;
	
	@Column(name = "USE_YN")
	private String useYn;
	
	@Column(name = "REMARK_1")
	private String remark1;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipInstLocSeq")
	@Builder.Default
	private List<RainRecvDate> rainRecvDate = new ArrayList<>();

	private boolean modify;
	
	public List<RainRecvDate> getRainRecvDate() {
		return rainRecvDate;
	}

	public void setRainRecvDate(List<RainRecvDate> rainRecvDate) {
		this.rainRecvDate = rainRecvDate;
	}

	public void changeModify() {
		modify = true;
	}
}
