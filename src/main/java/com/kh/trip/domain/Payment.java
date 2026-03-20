package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.PaymentMethod;
import com.kh.trip.domain.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAYMENTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_payments")
	@SequenceGenerator(name = "seq_payments", sequenceName = "SEQ_PAYMENTS", allocationSize = 1)
	@Column(name = "PAYMENT_NO")
	private Long paymentNo;

	@OneToOne // 예약 1건당 결제 1건
	@JoinColumn(name = "BOOKING_NO", nullable = false, unique = true)
	private Booking booking;

	@Column(name = "PAYMENT_ID", nullable = false, length = 100, unique = true)
	private String paymentId;

	@Column(name = "STORE_ID", length = 100)
	private String storeId;

	@Column(name = "CHANNEL_KEY", length = 200)
	private String channelKey;

	@Column(name = "ORDER_NAME", nullable = false, length = 200)
	private String orderName;

	@Column(name = "PAYMENT_AMOUNT", nullable = false)
	private Long paymentAmount;

	@Builder.Default
	@Column(name = "CURRENCY", nullable = false, length = 10)
	private String currency = "KRW";

	@Enumerated(EnumType.STRING)
	@Column(name = "PAY_METHOD", nullable = false, length = 30)
	private PaymentMethod payMethod;

	@Column(name = "PG_PROVIDER", length = 30)
	private String pgProvider;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_STATUS", nullable = false, length = 20)
	private PaymentStatus paymentStatus = PaymentStatus.READY;

	@Column(name = "APPROVED_AT")
	private LocalDateTime approvedAt;

	@Column(name = "CANCELED_AT")
	private LocalDateTime canceledAt;

	@Builder.Default
	@Column(name = "REFUND_AMOUNT", nullable = false)
	private Long refundAmount = 0L;

	@Column(name = "FAIL_REASON", length = 500)
	private String failReason;

	@Lob
	@Column(name = "RAW_RESPONSE")
	private String rawResponse;
}