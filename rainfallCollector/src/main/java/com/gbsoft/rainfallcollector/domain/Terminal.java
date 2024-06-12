package com.gbsoft.rainfallcollector.domain;

import static lombok.AccessLevel.*;

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
@Table(name = "terminal")
@Entity
public class Terminal extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "terminal_id")
	private Long id;

	@Column(name = "terminal_uuid", unique=true)
	private String terminalUuid;

	private String macaddress;

	@Column(name = "start_date_time")
	private LocalDateTime startDateTime;

	@Column(name = "is_powered")
	private boolean isPowered;


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "terminal")
	@Builder.Default
	private List<RainFall> rainFalls = new ArrayList<>();

	public List<RainFall> getRainFalls() {
		return rainFalls;
	}

	public void setRainFalls(List<RainFall> rainFalls) {
		this.rainFalls = rainFalls;
	}

	public void reboot(LocalDateTime dateTime) {
		this.startDateTime = dateTime;
		this.isPowered = true;
	}

	public void powerOff() {
		this.isPowered = false;
	}
}
