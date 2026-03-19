package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.CouponStatus;
import com.kh.trip.domain.enums.DiscountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COUPONS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Coupon extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_coupons")
	@SequenceGenerator(name = "seq_coupons", sequenceName = "SEQ_COUPONS", allocationSize = 1)
	@Column(name = "COUPON_NO")
	private Long couponNo;

	@Column(name = "ISSUED_BY_USER_NO", nullable = false)
	private Long issuedByUserNo;

	@Column(name = "COUPON_NAME", nullable = false, length = 100)
	private String couponName;

	@Column(name = "COUPON_CODE", nullable = false, length = 50, unique = true)
	private String couponCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "DISCOUNT_TYPE", nullable = false, length = 20)
	private DiscountType discountType;

	@Column(name = "DISCOUNT_VALUE", nullable = false)
	private Long discountValue;

	@Column(name = "MAX_DISCOUNT_AMOUNT")
	private Long maxDiscountAmount;

	@Builder.Default
	@Column(name = "MIN_ORDER_AMOUNT", nullable = false)
	private Long minOrderAmount = 0L;

	@Column(name = "START_DATE", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "END_DATE", nullable = false)
	private LocalDateTime endDate;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 20)
	private CouponStatus status = CouponStatus.ACTIVE;
}
