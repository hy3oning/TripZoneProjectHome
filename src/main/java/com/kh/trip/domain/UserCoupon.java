package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.UserCouponStatus;

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
@Table(name = "USER_COUPONS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserCoupon extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_coupons")
	@SequenceGenerator(name = "seq_user_coupons", sequenceName = "SEQ_USER_COUPONS", allocationSize = 1)
	@Column(name = "USER_COUPON_NO")
	private Long userCouponNo;

	@Column(name = "USER_NO", nullable = false)
	private Long userNo;

	@Column(name = "COUPON_NO", nullable = false)
	private Long couponNo;

	@Builder.Default
	@Column(name = "ISSUED_AT", nullable = false)
	private LocalDateTime issuedAt = LocalDateTime.now();

	@Column(name = "USED_AT")
	private LocalDateTime usedAt;

	@Column(name = "EXPIRED_AT")
	private LocalDateTime expiredAt;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 20)
	private UserCouponStatus status = UserCouponStatus.ISSUED;
}