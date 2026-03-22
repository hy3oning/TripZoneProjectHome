package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOKINGS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Booking extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bookings")
	@SequenceGenerator(name = "seq_bookings", sequenceName = "SEQ_BOOKINGS", allocationSize = 1)
	@Column(name = "BOOKING_NO")
	private Long bookingNo;

	@ManyToOne // 회원 1명이 예약 여러 건 가능
	@JoinColumn(name = "USER_NO", nullable = false)
	private User user;

	@ManyToOne // 객실 1개에 예약 여러건 가능
	@JoinColumn(name = "ROOM_NO", nullable = false)
	private Room room;

	@OneToOne
	@JoinColumn(name = "USER_COUPON_NO", unique = true)
	private UserCoupon userCoupon;

	@Column(name = "CHECK_IN_DATE", nullable = false)
	private LocalDateTime checkInDate;

	@Column(name = "CHECK_OUT_DATE", nullable = false)
	private LocalDateTime checkOutDate;

	@Column(name = "GUEST_COUNT", nullable = false)
	private Integer guestCount;

	@Column(name = "PRICE_PER_NIGHT", nullable = false)
	private Long pricePerNight;

	@Builder.Default
	@Column(name = "DISCOUNT_AMOUNT", nullable = false)
	private Long discountAmount = 0L;

	@Column(name = "TOTAL_PRICE", nullable = false)
	private Long totalPrice;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 20)
	private BookingStatus status = BookingStatus.PENDING;

	@Column(name = "REQUEST_MESSAGE", length = 500)
	private String requestMessage;
	
	public void changeStatus(com.kh.trip.domain.enums.BookingStatus status) {
		this.status = status;
	}
}