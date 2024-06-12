package com.gbsoft.rainfallcollector.domain;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import lombok.Getter;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity extends BaseTimeEntity {

//	@CreatedBy
//	@Column(updatable = false, name = "created_by")
//	private String createdBy;
//
//
//	@LastModifiedBy
//	@Column(name = "modified_by")
//	private String modifiedBy;
	
	@CreatedBy
	@Column(updatable = false, name = "CRT_USER")
	private String crtUser;
	
	@LastModifiedBy
	@Column(name = "UPDT_USER")
	private String updtUser;
}
