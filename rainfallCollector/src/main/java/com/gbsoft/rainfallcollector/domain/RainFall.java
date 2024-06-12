package com.gbsoft.rainfallcollector.domain;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "rainfall")
public class RainFall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "terminalId")
	private Terminal terminal;

	private Double rainfallPerOneMinute;
	private Double rainfallPerOneHour;

	private LocalDateTime dateTime;

}