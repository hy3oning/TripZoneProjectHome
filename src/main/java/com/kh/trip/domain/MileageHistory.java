package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.MileageChangeType;
import com.kh.trip.domain.enums.MileageHistoryStatus;

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
@Table(name = "MILEAGE_HISTORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MileageHistory extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mileage_history")
	@SequenceGenerator(name = "seq_mileage_history", sequenceName = "SEQ_MILEAGE_HISTORY", allocationSize = 1)
	@Column(name = "MILEAGE_HISTORY_NO")
	private Long mileageHistoryNo;

	// 회원 1명에게 이력 여러건
	@ManyToOne
	@JoinColumn(name = "USER_NO", nullable = false)
	private User user;

	// 예약 1건과 연결된 마일리지 이력 여러 건
	@ManyToOne
	@JoinColumn(name = "BOOKING_NO")
	private Booking booking;

	// 결제 1건에 마일리지 이력 여러건
	@ManyToOne
	@JoinColumn(name = "PAYMENT_NO")
	private Payment payment;

	@Enumerated(EnumType.STRING)
	@Column(name = "CHANGE_TYPE", nullable = false, length = 20)
	private MileageChangeType changeType;

	@Column(name = "CHANGE_AMOUNT", nullable = false)
	private Long changeAmount;

	@Column(name = "BALANCE_AFTER", nullable = false)
	private Long balanceAfter;

	@Column(name = "REASON", nullable = false, length = 200)
	private String reason;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 20)
	private MileageHistoryStatus status = MileageHistoryStatus.NORMAL;

	@Column(name = "EXPIRED_AT")
	private LocalDateTime expiredAt;
}