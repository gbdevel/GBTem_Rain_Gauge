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
@Table(name = "constructData")
@Entity
public class Construct {

	@Id
	@GeneratedValue
	@Column(name = "seq")
	private int seq;

	@Column(name = "constructName")
	private String constructName;
	
	@Column(name = "regDate")
	private LocalDateTime regDate;
	
	@Column(name = "constructLogo")
	private String constructLogo;
	
	@Column(name = "constructAddr1")
	private String constructAddr1;
	
	@Column(name = "constructAddr2")
	private String constructAddr2;
	
	@Column(name = "constructAddr3")
	private String constructAddr3;
	
	@Column(name = "constructId")
	private String constructId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "constructSeq")
	@Builder.Default
	private List<Onsiteinfo> onsiteinfos = new ArrayList<>();
	
	public List<Onsiteinfo> getOnsiteinfo() {
		return onsiteinfos;
	}

	public void setOnsiteinfo(List<Onsiteinfo> onsiteinfos) {
		this.onsiteinfos = onsiteinfos;
	}
	
	  @OneToMany(fetch = FetchType.LAZY, mappedBy = "custSeq")
	@Builder.Default
	private List<EquipByCust> equipByCusts = new ArrayList<>();
	
	public List<EquipByCust> getEquipByCust() {
		return equipByCusts;
	}

	public void setEquipByCust(List<EquipByCust> equipByCusts) {
		this.equipByCusts = equipByCusts;
	}
}
