package com.kh.trip.domain;

import java.math.BigDecimal;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.LodgingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LODGINGS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Lodging extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lodgings")
	@SequenceGenerator(name = "seq_lodgings", sequenceName = "SEQ_LODGINGS", allocationSize = 1)
	@Column(name = "LODGING_NO")
	private Long lodgingNo;

	@ManyToOne // 호스트 1명이 숙소 여러 개를 가질 수 있음
	@JoinColumn(name = "HOST_NO", nullable = false)
	private HostProfile hostProfile;

	@Column(name = "LODGING_NAME", nullable = false, length = 200)
	private String lodgingName;

	@Column(name = "LODGING_TYPE", nullable = false, length = 50)
	private String lodgingType;

	@Column(name = "REGION", nullable = false, length = 100)
	private String region;

	@Column(name = "ADDRESS", nullable = false, length = 300)
	private String address;

	@Column(name = "DETAIL_ADDRESS", length = 300)
	private String detailAddress;

	@Column(name = "ZIP_CODE", length = 20)
	private String zipCode;

	@Column(name = "LATITUDE", precision = 10, scale = 7)
	private BigDecimal latitude;

	@Column(name = "LONGITUDE", precision = 10, scale = 7)
	private BigDecimal longitude;

	@Column(name = "DESCRIPTION", length = 2000)
	private String description;

	@Column(name = "CHECK_IN_TIME", length = 20)
	private String checkInTime;

	@Column(name = "CHECK_OUT_TIME", length = 20)
	private String checkOutTime;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 20)
	private LodgingStatus status = LodgingStatus.ACTIVE;

	public void changeName(String lodgingName) {
		this.lodgingName = lodgingName;
	}

	public void changeType(String lodgingType) {
		this.lodgingType = lodgingType;
	}

	public void changeRegion(String region) {
		this.region = region;
	}

	public void changeStatus(LodgingStatus status) {
		this.status = status;
	}

	public void changeAddress(String address, String detailAddress, String zipCode) {
		this.address = address;
		this.detailAddress = detailAddress;
		this.zipCode = zipCode;
	}

	public void changeLocation(BigDecimal latitude, BigDecimal longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void changeDescription(String description) {
		this.description = description;
	}

	public void changeCheckTime(String checkInTime, String checkOutTime) {
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
	}

}
