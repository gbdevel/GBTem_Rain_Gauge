package com.gbsoft.rainfallcollector.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import lombok.Getter;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseTimeEntity {

//	@CreatedDate
//	@Column(updatable = false, name = "created_at")
//	private LocalDateTime createdAt;
//
//	@LastModifiedDate
//	@Column(name = "modified_at")
//	private LocalDateTime modifiedAt;
	
	@CreatedDate
	@Column(updatable = false, name = "CRT_DT")
	private LocalDateTime crtDt;

	@LastModifiedDate
	@Column(name = "UPDT_DT")
	private LocalDateTime updtDt;
}
