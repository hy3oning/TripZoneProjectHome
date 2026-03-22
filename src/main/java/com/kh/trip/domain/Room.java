package com.kh.trip.domain;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.RoomStatus;

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
@Table(name = "ROOMS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Room extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rooms")
	@SequenceGenerator(name = "seq_rooms", sequenceName = "SEQ_ROOMS", allocationSize = 1)
	@Column(name = "ROOM_NO")
	private Long roomNo; // 객실 번호

	@ManyToOne // 숙소 1개 안에 객실 여러 개
	@JoinColumn(name = "LODGING_NO")
 	private Lodging lodging; // 숙소 번호

	@Column(name = "ROOM_NAME", nullable = false, length = 200)
	private String roomName; // 객실명

	@Column(name = "ROOM_TYPE", nullable = false, length = 50)
	private String roomType; // 객실 유형

	@Column(name = "MAX_GUEST_COUNT", nullable = false)
	private Integer maxGuestCount; // 최대 수용인원

	@Column(name = "PRICE_PER_NIGHT", nullable = false)
	private Long pricePerNight; // 1박 기준가격

	@Builder.Default
	@Column(name = "ROOM_COUNT", nullable = false)
	private Integer roomCount = 1; // 동일 객실 수

	@Column(name = "DESCRIPTION", length = 2000)
	private String description; // 객실 설명

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 20)
	private RoomStatus status = RoomStatus.AVAILABLE; // 객실 상태
	
	public void changeName(String roomName) {
		this.roomName = roomName;
	}

	public void changeType(String roomType) {
		this.roomType = roomType;
	}

	public void changeMaxGuestCount(Integer maxGuestCount) {
		this.maxGuestCount = maxGuestCount;
	}

	public void changePricePerNight(Long pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public void changeRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public void changeDescription(String description) {
		this.description = description;
	}

	public void changeStatus(RoomStatus status) {
		this.status = status;
	}
}