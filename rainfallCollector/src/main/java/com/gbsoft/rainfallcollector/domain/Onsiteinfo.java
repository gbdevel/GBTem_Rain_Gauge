package com.gbsoft.rainfallcollector.domain;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
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
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "onSiteInfor")
@Entity
public class Onsiteinfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "siteNo")
	private Long siteNo;
	
	@Column(name = "siteName")
	private String siteName;
	
	@Column(name = "constStartDate")
	private LocalDateTime constStartDate;
	
	@Column(name = "postCode")
	private String postCode;
	
	@Column(name = "addr1")
	private String addr1;
	
	@Column(name = "addr2")
	private String addr2;
	
	@Column(name = "addr3")
	private String addr3;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "regDate")
	private LocalDateTime regDate;
	
	@Column(name = "modDate")
	private LocalDateTime modDate;
	
	@Column(name = "userNo")
	private Long userNo;
	
	@Column(name = "siteCd")
	private String siteCd;
	
	@ManyToOne
	@JoinColumn(name = "constructSeq")
	private Construct constructSeq;

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "siteInfoIdx")
	@Builder.Default
	private List<EquipInstLocation> equipInstLocations = new ArrayList<>();
	
	public List<EquipInstLocation> getEquipInstLocation() {
		return equipInstLocations;
	}

	public void setEquipInstLocation(List<EquipInstLocation> equipInstLocations) {
		this.equipInstLocations = equipInstLocations;
	}
}
