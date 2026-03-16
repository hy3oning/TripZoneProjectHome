package com.kh.trip.domain.common;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {

	@Column(name="REG_DATE")
	private LocalDateTime regDate;

	@Column(name="UPD_DATE")
	private LocalDateTime updDate;

	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.regDate = now;
		this.updDate = now;
	}

	@PreUpdate
	public void preUpdate() {
		this.updDate = LocalDateTime.now();
	}

}
